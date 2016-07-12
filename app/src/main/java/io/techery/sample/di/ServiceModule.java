package io.techery.sample.di;

import com.github.scribejava.core.oauth.OAuth20Service;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.Janet;
import io.techery.presenta.di.ApplicationScope;
import io.techery.sample.service.interactor.ApiInteractor;
import io.techery.sample.service.interactor.AuthInteractor;
import io.techery.sample.service.interactor.ServiceInteractor;
import io.techery.sample.storage.PreferenceWrapper;

@Module(
        includes = {
                JanetModule.class,
                ApiModule.class
        })
public class ServiceModule {

    @ApplicationScope
    @Provides
    AuthInteractor provideAuthManager(Janet janet, OAuth20Service oAuthService, PreferenceWrapper preferenceWrapper) {
        return new AuthInteractor(janet, oAuthService, preferenceWrapper);
    }

    @ApplicationScope
    @Provides
    ApiInteractor provideApiManager(Janet janet) {
        return new ApiInteractor(janet);
    }

    @ApplicationScope
    @Provides
    ServiceInteractor provideServiceInteractor(Janet janet) {
        return new ServiceInteractor(janet);
    }
}
