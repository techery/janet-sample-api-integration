package io.techery.github.api.http.provider;

import io.techery.github.api.http.EnvParams;
import io.techery.github.api.http.service.GitHubAuthService;

public class AuthorizedHttpServiceProvider extends SimpleHttpServiceProvider {

    public AuthorizedHttpServiceProvider(EnvParams envParams) {
        super(envParams);
    }

    @Override
    public GitHubAuthService provide() {
        return new GitHubAuthService(super.provide());
    }
}
