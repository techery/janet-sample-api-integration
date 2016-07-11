package io.techery.github.api.http.service;

import io.techery.github.api.api_common.BaseHttpAction;
import io.techery.github.api.http.EnvParams;

import io.techery.janet.ActionHolder;
import io.techery.janet.ActionService;
import io.techery.janet.ActionServiceWrapper;
import io.techery.janet.JanetException;

public class GitHubBaseService extends ActionServiceWrapper {

    private final EnvParams env;

    public GitHubBaseService(ActionService actionService, EnvParams env) {
        super(actionService);
        this.env = env;
    }

    @Override protected <A> boolean onInterceptSend(ActionHolder<A> holder) throws JanetException {
        if (holder.action() instanceof BaseHttpAction) {
            prepareHttpAction((BaseHttpAction) holder.action());
        }
        return false;
    }

    @Override protected <A> void onInterceptCancel(ActionHolder<A> holder) {
    }

    @Override protected <A> void onInterceptStart(ActionHolder<A> holder) {
    }

    @Override protected <A> void onInterceptProgress(ActionHolder<A> holder, int progress) {
    }

    @Override protected <A> void onInterceptSuccess(ActionHolder<A> holder) {
    }

    @Override protected <A> boolean onInterceptFail(ActionHolder<A> holder, JanetException e) {
        return false;
    }

    private void prepareHttpAction(BaseHttpAction action) {
        action.setApiVersionForAccept(env.apiVersion());
        action.setLanguageHeader(env.appLanguage());
    }
}
