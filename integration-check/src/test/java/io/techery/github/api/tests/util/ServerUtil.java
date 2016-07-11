package io.techery.github.api.tests.util;

import io.techery.github.api.api_common.BaseHttpAction;
import io.techery.github.api.api_common.error.ErrorResponse;

public class ServerUtil {
    private ServerUtil() {}

    public static void waitForServerLag() {
        synchronized (Thread.currentThread()) {
            try {
                Thread.currentThread().wait(300L);
            } catch (InterruptedException e) {
            }
        }
    }

    public static boolean hasError(BaseHttpAction action, String error) {
        ErrorResponse errorResponse = action.errorResponse();
        if (errorResponse == null) return false;
        //
        return errorResponse.message().contains(error);
    }
}
