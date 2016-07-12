package io.techery.sample.service.interactor;

import io.techery.janet.ActionPipe;
import io.techery.janet.Janet;
import io.techery.sample.service.command.ContributorsCommand;
import io.techery.sample.service.command.ReposCommand;
import io.techery.sample.service.command.UserCommand;
import rx.schedulers.Schedulers;

public class ServiceInteractor {

    private final ActionPipe<ReposCommand> reposActionPipe;

    private final ActionPipe<ContributorsCommand> contributorsActionPipe;

    private final ActionPipe<UserCommand> userActionPipe;

    public ServiceInteractor(Janet janet) {
        reposActionPipe = janet.createPipe(ReposCommand.class, Schedulers.io());
        contributorsActionPipe = janet.createPipe(ContributorsCommand.class, Schedulers.io());
        userActionPipe = janet.createPipe(UserCommand.class, Schedulers.io());
    }

    public ActionPipe<ReposCommand> reposActionPipe() {
        return reposActionPipe;
    }

    public ActionPipe<ContributorsCommand> contributorsActionPipe() {
        return contributorsActionPipe;
    }

    public ActionPipe<UserCommand> userActionPipe() {
        return userActionPipe;
    }
}
