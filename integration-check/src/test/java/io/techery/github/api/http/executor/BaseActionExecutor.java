package io.techery.github.api.http.executor;

import io.techery.github.api.api_common.BaseHttpAction;
import io.techery.github.api.http.provider.JanetProvider;

import io.techery.janet.ActionPipe;
import io.techery.janet.ActionState;
import io.techery.janet.Janet;
import ru.yandex.qatools.allure.annotations.Step;
import rx.Observable;

public class BaseActionExecutor<T extends JanetProvider> implements ActionExecutor {

    private T janetProvider;
    private Janet janet;

    public BaseActionExecutor(T janetProvider) {
        this.janetProvider = janetProvider;
        this.janet = janetProvider.provide();
    }

    protected final T getJanetProvider() {
        return janetProvider;
    }

    @Step("Execute: {0}")
    public <T extends BaseHttpAction> T execute(T action) {
        final ActionPipe<T> pipe = (ActionPipe<T>) janet.createPipe(action.getClass());
        final Observable<ActionState<T>> observable = pipe.createObservable(action);
        return observable.toBlocking().last().action;
    }
}
