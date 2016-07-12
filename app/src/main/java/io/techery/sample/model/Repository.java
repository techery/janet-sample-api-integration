package io.techery.sample.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.nio.ByteBuffer;

import io.techery.github.api.repos.model.RepositoryModel;
import io.techery.snapper.model.Indexable;

@Value.Immutable
public abstract class Repository implements RepositoryModel, Indexable {
    @Gson.Ignore
    @Value.Default
    @Override
    public byte[] index() {
        return ByteBuffer.allocate(8).putLong(id()).array();
    }
}
