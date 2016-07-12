package io.techery.github.api.repos.model;

import com.google.gson.annotations.SerializedName;

import io.techery.github.api.user.model.User;
import io.techery.github.api.util.Nullable;

public interface RepositoryModel {
    @SerializedName("id")
    Long id();
    @SerializedName("name")
    String name();
    @Nullable
    @SerializedName("description")
    String description();
    @SerializedName("html_url")
    String url();
    @SerializedName("owner")
    User owner();
}
