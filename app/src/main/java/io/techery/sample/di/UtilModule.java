package io.techery.sample.di;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import io.techery.github.api.common.converter.GsonProvider;
import io.techery.presenta.di.ApplicationScope;

@Module
public class UtilModule {

    @ApplicationScope
    @Provides
    Gson provideGson() {
        GsonProvider provider = new GsonProvider();
        return provider.provideGson();
    }
}
