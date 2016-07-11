package io.techery.github.api.api_common.model;

import com.google.gson.annotations.SerializedName;

import org.immutables.value.Value;

public interface PaginatedParams {
    @Value.Parameter
    @SerializedName("page")
    int page();

    @Value.Parameter
    @SerializedName("per_page")
    int perPage();
}
