package io.techery.github.api.common.error;

import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.HashMap;

@Gson.TypeAdapters
@Value.Immutable
public interface ErrorResponse {
    @SerializedName("message") String message();
}
