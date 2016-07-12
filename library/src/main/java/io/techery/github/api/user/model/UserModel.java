package io.techery.github.api.user.model;

import com.google.gson.annotations.SerializedName;

import io.techery.github.api.util.Nullable;

public interface UserModel {
    @SerializedName("id")
    Long id();
    @SerializedName("login")
    String login();
    @SerializedName("avatar_url")
    String avatarUrl();
    @SerializedName("html_url")
    String url();
    @Nullable
    @SerializedName("name")
    String name();
}
