package io.techery.github.api.repos.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public interface Repository extends RepositoryModel {
}
