package io.techery.sample.di;

import android.content.Context;

import java.util.Set;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionService;
import io.techery.janet.CommandActionService;
import io.techery.janet.Janet;
import io.techery.presenta.di.ApplicationScope;
import io.techery.sample.service.DaggerCommandServiceWrapper;
import io.techery.sample.service.TimberServiceWrapper;
import io.techery.sample.storage.CacheServiceWrapper;

@Module
public class JanetModule {

    @ApplicationScope
    @Provides(type = Provides.Type.SET)
    ActionService provideCommandService(@ApplicationScope  Context context) {
        return new CacheServiceWrapper(new DaggerCommandServiceWrapper(new CommandActionService(), context), context);
    }

    @ApplicationScope
    @Provides
    Janet provideJanet(Set<ActionService> services) {
        Janet.Builder builder = new Janet.Builder();
        for (ActionService service : services) {
            builder.addService(new TimberServiceWrapper(service));
        }
        return builder.build();
    }
}
