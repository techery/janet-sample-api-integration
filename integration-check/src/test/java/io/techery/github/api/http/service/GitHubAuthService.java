package io.techery.github.api.http.service;

import java.util.Base64;

import io.techery.github.api.common.AuthorizedHttpAction;
import io.techery.github.api.user.UserHttpAction;
import io.techery.github.api.user.model.UserModel;
import io.techery.janet.ActionHolder;
import io.techery.janet.ActionService;
import io.techery.janet.ActionServiceWrapper;
import io.techery.janet.JanetException;

public class GitHubAuthService extends ActionServiceWrapper {

    public GitHubAuthService(ActionService actionService) {
        super(actionService);
    }

    private UserCredential credential;

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setCredential(String username, String password) {
        credential = new UserCredential(username, password);
    }

    @Override
    protected <A> boolean onInterceptSend(ActionHolder<A> holder) throws JanetException {
        if (holder.action() instanceof AuthorizedHttpAction) {
            tryAuthorizeAction((AuthorizedHttpAction) holder.action());
        }
        return false;
    }

    @Override
    protected <A> void onInterceptCancel(ActionHolder<A> holder) {
    }

    @Override
    protected <A> void onInterceptStart(ActionHolder<A> holder) {
    }

    @Override
    protected <A> void onInterceptProgress(ActionHolder<A> holder, int progress) {
    }

    @Override
    protected <A> void onInterceptSuccess(ActionHolder<A> holder) {
        if (holder.action() instanceof UserHttpAction) {
            user = ((UserHttpAction) holder.action()).response();
        }
    }

    @Override
    protected <A> boolean onInterceptFail(ActionHolder<A> holder, JanetException e) {
        return false;
    }

    private void tryAuthorizeAction(AuthorizedHttpAction action) {
        if (credential == null) return;

        action.setAuthorizationHeader("Basic " +
                Base64.getEncoder().encodeToString((credential.username + ":" + credential.password).getBytes()));
    }

    private static class UserCredential {
        private final String username;

        private final String password;

        private UserCredential(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
