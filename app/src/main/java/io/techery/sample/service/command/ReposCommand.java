package io.techery.sample.service.command;

import java.util.List;

import javax.inject.Inject;

import io.techery.github.api.repos.ReposHttpAction;
import io.techery.github.api.user.model.UserModel;
import io.techery.janet.Command;
import io.techery.janet.command.annotations.CommandAction;
import io.techery.sample.model.ImmutableRepository;
import io.techery.sample.model.Repository;
import io.techery.sample.service.interactor.ApiInteractor;
import io.techery.sample.storage.CacheOptions;
import io.techery.sample.storage.CachedAction;
import io.techery.sample.storage.ImmutableCacheOptions;
import rx.Observable;

@CommandAction
public class ReposCommand extends Command<List<Repository>> implements CachedAction<Repository> {


    @Inject
    ApiInteractor apiInteractor;

    private List<Repository> repositories;

    private final UserModel user;

    private final boolean restoreFromCache;

    public ReposCommand(UserModel user, boolean restoreFromCache) {
        this.user = user;
        this.restoreFromCache = restoreFromCache;
    }

    @Override
    protected void run(CommandCallback<List<Repository>> callback) throws Throwable {
        if (repositories != null) {
            callback.onSuccess(repositories);
        } else {
            apiInteractor.reposActionPipe()
                    .createObservableResult(new ReposHttpAction(user))
                    .flatMap(action ->
                            Observable.from(action.response())
                                    .map(repo ->
                                            ImmutableRepository.builder()
                                                    .from(repo)
                                                    .build()
                                    )
                                    .cast(Repository.class)
                                    .toList()
                    )
                    .doOnNext(contributors -> this.repositories = contributors)
                    .subscribe(callback::onSuccess, callback::onFail);
        }
    }

    public boolean isOwn(UserModel user) {
        return user != null && user.id().equals(this.user.id());
    }

    @Override
    public List<Repository> getData() {
        return repositories;
    }

    @Override
    public void onRestore(List<Repository> data) {
        repositories = data;
    }

    @Override
    public CacheOptions getOptions() {
        String key = user != null ? user.login() : null;
        return ImmutableCacheOptions.builder()
                .type(ImmutableRepository.class)
                .key(key)
                .restoreFromCache(restoreFromCache)
                .build();
    }
}
