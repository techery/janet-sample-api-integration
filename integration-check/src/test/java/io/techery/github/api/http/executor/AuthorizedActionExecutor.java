package io.techery.github.api.http.executor;

import io.techery.github.api.http.provider.AuthorizedJanetProvider;
import io.techery.github.api.user.UserHttpAction;
import io.techery.github.api.user.model.UserModel;

public class AuthorizedActionExecutor extends BaseActionExecutor<AuthorizedJanetProvider> {

    public AuthorizedActionExecutor(String username, String password) {
        super(new AuthorizedJanetProvider());
        getJanetProvider().getService().setCredential(username, password);
    }

    public UserHttpAction authorize() {
        return execute(new UserHttpAction());
    }

    public UserModel getCurrentUser() {
        return getJanetProvider().user();
    }
}
