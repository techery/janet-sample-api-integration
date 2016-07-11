package io.techery.github.api.api_common.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.util.Date;
import java.util.ServiceLoader;

public class GsonProvider {

    public GsonBuilder provideBuilder() {
        GsonBuilder builder = new GsonBuilder()
                .setExclusionStrategies(new SerializedNameExclusionStrategy())
                //
                .registerTypeAdapterFactory(new SmartEnumTypeAdapterFactory("unknown"))
                .registerTypeAdapter(Date.class, new DateTimeSerializer())
                .registerTypeAdapter(Date.class, new DateTimeDeserializer());
        // models
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            builder.registerTypeAdapterFactory(factory);
        }
        return builder;
    }


    public Gson provideGson() {
        return provideBuilder().create();
    }

}
