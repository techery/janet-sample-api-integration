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
import io.techery.presenta.mortarscreen.presenter.WithPresenter;
import io.techery.sample.model.Repository;
import io.techery.sample.model.User;
import io.techery.sample.service.command.ContributorsCommand;
import io.techery.sample.service.interactor.ServiceInteractor;
import io.techery.sample.ui.ScreenPresenter;
import io.techery.sample.ui.view.ContributorsView;


@WithPresenter(ContributorsScreen.Presenter.class)
public class ContributorsScreen extends Screen {

    private static final Comparator<User> COMPARATOR = (lhs, rhs) -> lhs.login().compareTo(rhs.login());

    private final Repository repository;

    public ContributorsScreen(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String getTitle() {
        return repository.name();
    }

    @Override
    public View createView(Context context) {
        return new ContributorsView(context);
    }

    public class Presenter extends ScreenPresenter<ContributorsView> {

        @Inject
        ServiceInteractor interactor;

        private final ReadActionPipe<ContributorsCommand> actionPipe;

        public Presenter(PresenterInjector injector) {
            super(injector);
            actionPipe = interactor.contributorsActionPipe()
                    .filter(action -> action.getRepository().equals(repository));
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            bindPipeCached(actionPipe)
                    .onStart((command) -> {
                        List<User> data = command.getResult();
                        if (data != null && !data.isEmpty()) {
                            onReceived(command.getResult());
                        } else {
                            getView().setRefreshing(true);
                        }
                    })
                    .onFail((command, throwable) -> {
                        getView().setRefreshing(false);
                        Toast.makeText(activityHolder.activity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                .show();
                    })
                    .onSuccess(command -> onReceived(command.getResult()));

            loadContributors(true);
        }

        private void onReceived(List<User> contributors) {
            getView().setRefreshing(false);
            getView().updateItems(Queryable.from(contributors)
                    .sort(COMPARATOR)
                    .toList());
        }

        public void loadContributors(boolean restoreFromCache) {
            interactor.contributorsActionPipe().send(new ContributorsCommand(repository, restoreFromCache));
        }

        public void loadContributors() {
            loadContributors(false);
        }

        public void selectContributor(User user) {
            Flow.get(getView()).set(new ReposScreen(user));
        }
    }

}
