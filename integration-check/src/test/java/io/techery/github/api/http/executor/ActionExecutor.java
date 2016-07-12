package io.techery.github.api.http.executor;

import io.techery.github.api.common.BaseHttpAction;

import ru.yandex.qatools.allure.annotations.Step;

public interface ActionExecutor {

    @Step("Execute: {0}")
    <T extends BaseHttpAction> T execute(T action);
}
