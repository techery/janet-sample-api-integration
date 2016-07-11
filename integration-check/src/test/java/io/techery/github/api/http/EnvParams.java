package io.techery.github.api.http;

import org.immutables.value.Value;

@Value.Immutable
public interface EnvParams {

    String apiUrl();
    String apiVersion();
    String appLanguage();

}
