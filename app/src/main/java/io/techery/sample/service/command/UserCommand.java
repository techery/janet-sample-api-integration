package io.techery.sample.service.command;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.techery.github.api.user.UserHttpAction;
import io.techery.janet.Command;
import io.techery.janet.command.annotations.CommandAction;
import io.techery.sample.model.ImmutableUser;
import io.techery.sample.model.User;
import io.techery.sample.service.interactor.ApiInteractor;
import io.techery.sample.storage.CacheOptions;
import io.techery.sample.storage.CachedAction;
import io.techery.sample.storage.ImmutableCacheOptions;

@CommandAction
public class UserCommand extends Command<User> implements CachedAction<User> {

    @Inject
    ApiInteractor apiInteractor;

    private User user;

    @Override
    protected void run(CommandCallback<User> callback) throws Throwable {
        if (user != null) {
            callback.onSuccess(user);
        } else {
            apiInteractor.userActionPipe()
                    .createObservableResult(new UserHttpAction())
                    .map(action ->
                            ImmutableUser.builder()
                                    .from(action.response())
                                    .build()
                    )
                    .doOnNext(user -> this.user = user)
                    .subscribe(callback::onSuccess, callback::onFail);
        }
    }


    @Override
    public CacheOptions getOptions() {
        return ImmutableCacheOptions.builder()
                .key("current")
                .type(ImmutableUser.class)
                .build();
    }

    @Override
    public List<User> getData() {
        List<User> data = new ArrayList<>();
        if (user != null) {
            data.add(user);
        }
        return data;
    }

    @Override
    public void onRestore(List<User> data) {
        if (!data.isEmpty()) {
            user = data.get(0);
        }
    }
}
