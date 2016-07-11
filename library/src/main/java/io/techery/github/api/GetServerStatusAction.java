package io.techery.github.api;

import io.techery.github.api.api_common.BaseHttpAction;

import io.techery.janet.http.annotations.HttpAction;

@HttpAction("/")
public class GetServerStatusAction extends BaseHttpAction {

    public ServerState state() {
        if (statusCode() == 200) {
            return ServerState.AVAILABLE;
        } else if (statusCode() >= 500) {
            return ServerState.UNAVAILABLE;
        } else if (statusCode() == 0){
            return ServerState.INVALID_HOST;
        } else {
            return ServerState.UNKNOWN;
        }
    }

    public enum ServerState {
        AVAILABLE,
        UNAVAILABLE,
        INVALID_HOST,
        UNKNOWN
    }
}
