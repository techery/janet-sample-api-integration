package io.techery.github.api.http.provider;

import io.techery.github.api.http.service.GitHubAuthService;
import io.techery.github.api.user.model.User;

public class AuthorizedJanetProvider extends BaseJanetProvider<GitHubAuthService> {

    public AuthorizedJanetProvider() {
        super(new AuthorizedHttpServiceProvider(new SystemEnvProvider().provide()).provide());
    }

    public User user() {
        return getService().getUser();
    }
}
