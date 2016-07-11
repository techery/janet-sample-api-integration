package io.techery.github.api.http.provider;

import io.techery.janet.ActionService;
import io.techery.janet.Janet;

public class BaseJanetProvider<T extends ActionService> implements JanetProvider {

    private T service;

    public BaseJanetProvider(T service) {
        this.service = service;
    }

    public T getService() {
        return service;
    }

    @Override
    public Janet provide() {
        return new Janet.Builder().addService(service).build();
    }

}
