package io.techery.github.api.fixtures;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public interface UserCredential {
    @SerializedName("username")
    String username();
    @SerializedName("password")
    String password();
}
