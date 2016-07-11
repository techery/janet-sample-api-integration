package io.techery.github.api.http.util;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class CurlInterceptor implements Interceptor {

    private final CurlLogger logger;

    public CurlInterceptor(CurlLogger logger) {
        this.logger = logger;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request copy = request.newBuilder().build();
        String curl = new CurlBuilder(copy).build();
        logger.log(curl);
        return chain.proceed(request);
    }
}
