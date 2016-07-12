package io.techery.sample;


import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

import dagger.Component;
import io.techery.presenta.di.ApplicationScope;
import io.techery.sample.di.RootModule;
import io.techery.sample.presenta.ActivityHolder;
import io.techery.sample.service.CommandsComponent;
import io.techery.sample.service.interactor.ApiInteractor;
import io.techery.sample.service.interactor.AuthInteractor;
import io.techery.sample.service.interactor.ServiceInteractor;
import io.techery.sample.storage.PreferenceWrapper;

@ApplicationScope
@Component(modules = RootModule.class)
public interface AppComponent extends CommandsComponent {

    Gson gson();

    ActivityHolder activityHolder();

    OAuth20Service oAuthService();

    PreferenceWrapper preferenceWrapper();

    AuthInteractor authInteractor();

    ApiInteractor apiInteractor();

    ServiceInteractor serviceInteractor();

    void inject(App app);

}
