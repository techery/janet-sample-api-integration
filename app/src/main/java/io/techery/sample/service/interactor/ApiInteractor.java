package io.techery.sample.service.interactor;

import io.techery.github.api.repos.ContributorsHttpAction;
import io.techery.github.api.repos.ReposHttpAction;
import io.techery.github.api.user.UserHttpAction;
import io.techery.janet.ActionPipe;
import io.techery.janet.Janet;
import rx.schedulers.Schedulers;

public class ApiInteractor {

    private final ActionPipe<ReposHttpAction> reposActionPipe;

    private final ActionPipe<ContributorsHttpAction> contributorsActionPipe;

    private final ActionPipe<UserHttpAction> userActionPipe;

    public ApiInteractor(Janet janet) {
        reposActionPipe = janet.createPipe(ReposHttpAction.class, Schedulers.io());
        contributorsActionPipe = janet.createPipe(ContributorsHttpAction.class, Schedulers.io());
        userActionPipe = janet.createPipe(UserHttpAction.class, Schedulers.io());
    }

    public ActionPipe<ReposHttpAction> reposActionPipe() {
        return reposActionPipe;
    }

    public ActionPipe<ContributorsHttpAction> contributorsActionPipe() {
        return contributorsActionPipe;
    }

    public ActionPipe<UserHttpAction> userActionPipe() {
        return userActionPipe;
    }
}
