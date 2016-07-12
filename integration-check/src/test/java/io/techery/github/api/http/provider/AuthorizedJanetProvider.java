package io.techery.github.api.http.provider;

import io.techery.github.api.http.service.GitHubAuthService;
import io.techery.github.api.user.model.UserModel;

public class AuthorizedJanetProvider extends BaseJanetProvider<GitHubAuthService> {

    public AuthorizedJanetProvider() {
        super(new AuthorizedHttpServiceProvider(new SystemEnvProvider().provide()).provide());
    }

    public UserModel user() {
        return getService().getUser();
    }
}
