package io.techery.github.api.user.model;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters
@Value.Immutable
public interface User {
    @SerializedName("id")
    Long id();
    @SerializedName("login")
    String login();
    @SerializedName("avatar_url")
    String avatarUrl();
    @SerializedName("html_url")
    String url();
    @SerializedName("name")
    String name();
}
