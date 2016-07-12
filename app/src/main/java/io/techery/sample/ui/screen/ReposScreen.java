package io.techery.sample.ui.screen;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.innahema.collections.query.queriables.Queryable;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import io.techery.janet.ReadActionPipe;
import io.techery.janet.helper.ActionStateSubscriber;
import io.techery.presenta.mortarscreen.presenter.WithPresenter;
import io.techery.sample.model.Repository;
import io.techery.sample.model.User;
import io.techery.sample.service.command.ReposCommand;
import io.techery.sample.service.command.UserCommand;
import io.techery.sample.service.interactor.ServiceInteractor;
import io.techery.sample.ui.ScreenPresenter;
import io.techery.sample.ui.view.ReposView;

@WithPresenter(ReposScreen.Presenter.class)
public class ReposScreen extends Screen {

    private final static Comparator<Repository> COMPARATOR = (lhs, rhs) -> lhs.name().compareTo(rhs.name());

    private User arg;

    public ReposScreen(User arg) {
        this.arg = arg;
    }

    public ReposScreen() {
    }

    @Override
    public String getTitle() {
        if (arg != null) {
            return arg.login();
        }
        return null;
    }

    @Override
    public View createView(Context context) {
        return new ReposView(context);
    }

    public class Presenter extends ScreenPresenter<ReposView> {

        @Inject
        ServiceInteractor interactor;

        private User user;

        private final ReadActionPipe<ReposCommand> readActionPipe;

        public Presenter(PresenterInjector injector) {
            super(injector);
            this.user = ReposScreen.this.arg;
            readActionPipe = interactor.reposActionPipe()
                    .filter(action -> action.isOwn(user));
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            bindPipeCached(readActionPipe)
                    .onStart((action) -> {
                        List<Repository> repositories = action.getResult();
                        if (repositories != null && !repositories.isEmpty()) {
                            onReceived(action);
                        } else {
                            getView().setRefreshing(true);
                        }
                    })
                    .onFail((action, throwable) -> {
                        getView().setRefreshing(false);
                        Toast.makeText(activityHolder.activity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                .show();
                    })
                    .onSuccess(this::onReceived);

            loadRepos(true);
        }

        private void onReceived(ReposCommand command) {
            getView().setRefreshing(false);
            getView().updateItems(Queryable.from(command.getResult())
                    .sort(COMPARATOR)
                    .toList());
        }

        public void onError(Throwable throwable) {
            getView().setRefreshing(false);
            Toast.makeText(activityHolder.activity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        public void loadRepos(boolean restoreFromCache) {
            if (user == null) {
                bind(interactor.userActionPipe()
                        .createObservable(new UserCommand()))
                        .subscribe(new ActionStateSubscriber<UserCommand>()
                                .onStart(action -> getView().setRefreshing(true))
                                .onFail((action, throwable) -> onError(throwable))
                                .onSuccess(action -> {
                                    user = action.getResult();
                                    loadRepos(restoreFromCache);
                                }));
            } else {
                interactor.reposActionPipe().send(new ReposCommand(user, restoreFromCache));
            }
        }

        public void loadRepos() {
            loadRepos(false);
        }

        public void selectRepository(Repository repository) {
            Flow.get(getView()).set(new ContributorsScreen(repository));
        }

    }
}
