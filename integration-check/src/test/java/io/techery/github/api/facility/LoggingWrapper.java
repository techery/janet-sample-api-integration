package io.techery.github.api.facility;

import io.techery.janet.ActionHolder;
import io.techery.janet.ActionService;
import io.techery.janet.ActionServiceWrapper;
import io.techery.janet.JanetException;

public class LoggingWrapper extends ActionServiceWrapper {

    public LoggingWrapper(ActionService actionService) {
        super(actionService);
    }

    @Override protected <A> boolean onInterceptSend(ActionHolder<A> holder) {
        return false;
    }

    @Override protected <A> void onInterceptCancel(ActionHolder<A> holder) {
    }

    @Override protected <A> void onInterceptStart(ActionHolder<A> holder) {
        Log.logActionStart(holder);
    }

    @Override protected <A> void onInterceptProgress(ActionHolder<A> holder, int progress) {
    }

    @Override protected <A> void onInterceptSuccess(ActionHolder<A> holder) {
        Log.logActionSuccess(holder);
    }

    @Override protected <A> boolean onInterceptFail(ActionHolder<A> holder, JanetException e) {
        Log.logActionFail(holder, e);
        return false;
    }

}
