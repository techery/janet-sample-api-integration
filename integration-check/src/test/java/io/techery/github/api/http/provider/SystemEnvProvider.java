package io.techery.github.api.http.provider;

import io.techery.github.api.http.EnvParams;
import io.techery.github.api.http.ImmutableEnvParams;

public class SystemEnvProvider implements EnvProvider {
    @Override
    public EnvParams provide() {
        return ImmutableEnvParams.builder()
                .apiUrl(System.getenv("API_URL"))
                .apiVersion(System.getenv("API_VERSION"))
                .appLanguage(System.getenv("CLIENT_LANGUAGE"))
                .build();
    }
}
