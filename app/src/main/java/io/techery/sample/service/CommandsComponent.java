package io.techery.sample.service;

import io.techery.sample.service.command.ContributorsCommand;
import io.techery.sample.service.command.ReposCommand;
import io.techery.sample.service.command.UserCommand;
import io.techery.sample.service.oauth.AccessTokenCommand;

public interface CommandsComponent {

    void inject(AccessTokenCommand command);
    void inject(UserCommand command);
    void inject(ContributorsCommand command);
    void inject(ReposCommand command);

}
