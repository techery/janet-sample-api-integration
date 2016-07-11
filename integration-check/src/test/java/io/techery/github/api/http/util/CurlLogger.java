package io.techery.github.api.http.util;

public interface CurlLogger {

    void log(String message);

    class SystemCurlLogger implements CurlLogger {

        @Override public void log(String message) {
            System.out.println(message);
        }
    }
}
