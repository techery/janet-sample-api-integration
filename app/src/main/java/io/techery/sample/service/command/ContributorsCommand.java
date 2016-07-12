package io.techery.sample.service.command;

import java.util.List;

import javax.inject.Inject;

import io.techery.github.api.repos.ContributorsHttpAction;
import io.techery.github.api.repos.model.RepositoryModel;
import io.techery.janet.Command;
import io.techery.janet.command.annotations.CommandAction;
import io.techery.sample.model.ImmutableUser;
import io.techery.sample.model.User;
import io.techery.sample.service.interactor.ApiInteractor;
import io.techery.sample.storage.CacheOptions;
import io.techery.sample.storage.CachedAction;
import io.techery.sample.storage.ImmutableCacheOptions;
import rx.Observable;

@CommandAction
public class ContributorsCommand extends Command<List<User>> implements CachedAction<User> {


    @Inject
    ApiInteractor apiInteractor;

    private List<User> contributors;

    private final RepositoryModel repository;

    private final boolean restoreFromCache;

    public ContributorsCommand(RepositoryModel repository, boolean restoreFromCache) {
        this.repository = repository;
        this.restoreFromCache = restoreFromCache;
    }

    @Override
    protected void run(CommandCallback<List<User>> callback) throws Throwable {
        if (contributors != null) {
            callback.onSuccess(contributors);
        } else {
            apiInteractor.contributorsActionPipe()
                    .createObservableResult(new ContributorsHttpAction(repository))
                    .flatMap(action ->
                            Observable.from(action.response())
                                    .map(user ->
                                            ImmutableUser.builder()
                                                    .from(user)
                                                    .build()
                                    )
                                    .cast(User.class)
                                    .toList()
                    )
                    .doOnNext(contributors -> this.contributors = contributors)
                    .subscribe(callback::onSuccess, callback::onFail);
        }
    }

    public RepositoryModel getRepository() {
        return repository;
    }

    @Override
    public CacheOptions getOptions() {
        return ImmutableCacheOptions.builder()
                .type(ImmutableUser.class)
                .key(repository.id().toString())
                .restoreFromCache(restoreFromCache)
                .build();
    }

    @Override
    public List<User> getData() {
        return contributors;
    }

    @Override
    public void onRestore(List<User> data) {
        contributors = data;
    }
}
