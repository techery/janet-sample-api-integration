package io.techery.github.api.common;

import io.techery.janet.http.annotations.RequestHeader;

public abstract class AuthorizedHttpAction extends BaseHttpAction {

    @RequestHeader("Authorization") String authorizationHeader;

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }
}
