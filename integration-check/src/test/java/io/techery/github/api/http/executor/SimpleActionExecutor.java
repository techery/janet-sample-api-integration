package io.techery.github.api.http.executor;

import io.techery.github.api.http.provider.BaseJanetProvider;
import io.techery.github.api.http.provider.SimpleHttpServiceProvider;
import io.techery.github.api.http.provider.SimpleJanetProvider;
import io.techery.github.api.http.provider.SystemEnvProvider;

public class SimpleActionExecutor extends BaseActionExecutor<BaseJanetProvider> {

    public SimpleActionExecutor() {
        super(new SimpleJanetProvider(new SimpleHttpServiceProvider(new SystemEnvProvider().provide()).provide()));
    }
}
