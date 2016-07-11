package io.techery.github.api.fixtures.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import ie.corballis.fixtures.io.Resource;

import static com.google.common.collect.Maps.newHashMap;

public class DefaultFixtureReader implements FixtureReader {

    private Gson gson;

    public DefaultFixtureReader() {
        this.gson = new Gson();
    }

    public DefaultFixtureReader(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Map<String, JsonElement> read(Resource resource) throws IOException {
        Map<String, JsonElement> nodes = newHashMap();

        JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(resource.getInputStream()));
        JsonParser jsonParser = new JsonParser();
        JsonElement node = jsonParser.parse(jsonReader);

        Set<Map.Entry<String, JsonElement>> entries = node.getAsJsonObject().entrySet();
        entries.forEach(entry -> {
            nodes.put(entry.getKey(), entry.getValue());
        });
        return nodes;
    }

}
