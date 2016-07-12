package io.techery.github.api.user.model;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters
@Value.Immutable
public interface UpdateUserParams {
    @SerializedName("name")
    String name();
    @SerializedName("email")
    String email();
    @SerializedName("blog")
    String blog();
    @SerializedName("company")
    String company();
    @SerializedName("location")
    String location();
    @SerializedName("hireable")
    boolean hireable();
    @SerializedName("bio")
    String bio();
}
