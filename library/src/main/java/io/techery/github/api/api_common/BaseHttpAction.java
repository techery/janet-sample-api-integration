package io.techery.github.api.api_common;

import io.techery.github.api.api_common.error.ErrorResponse;
import io.techery.janet.http.annotations.RequestHeader;
import io.techery.janet.http.annotations.Response;
import io.techery.janet.http.annotations.Status;

public abstract class BaseHttpAction {
    @RequestHeader("Accept")
    String acceptHeader;

    @RequestHeader("Accept-Language")
    String languageHeader;

    //
    @Status
    int statusCode;

    @Response(min = 400)
    ErrorResponse errorResponse;

    ///////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ///////////////////////////////////////////////////////////////////////////
    public int statusCode() {
        return statusCode;
    }

    public ErrorResponse errorResponse() {
        return errorResponse;
    }

    public void setLanguageHeader(String languageHeader) {
        this.languageHeader = languageHeader;
    }

    public void setApiVersionForAccept(String version) {
        acceptHeader = "application/vnd.github.v" + version + "+json";
    }
}
