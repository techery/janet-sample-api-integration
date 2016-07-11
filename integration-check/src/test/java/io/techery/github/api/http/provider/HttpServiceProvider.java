package io.techery.github.api.http.provider;

import io.techery.janet.ActionService;

public interface HttpServiceProvider<T extends ActionService> {

    T provide();
}
