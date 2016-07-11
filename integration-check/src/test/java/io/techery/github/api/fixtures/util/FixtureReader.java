package io.techery.github.api.fixtures.util;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.Map;

import ie.corballis.fixtures.io.Resource;

public interface FixtureReader {

    Map<String, JsonElement> read(Resource resource) throws IOException;

}
