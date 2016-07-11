package io.techery.github.api.fixtures.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ie.corballis.fixtures.io.FixtureScanner;
import ie.corballis.fixtures.io.Resource;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public class BeanFactory {

    private Gson gson;
    private FixtureScanner scanner;
    private FixtureReader reader;

    private Cache<String, JsonElement> fixtures = CacheBuilder.newBuilder().build();

    public BeanFactory() {
        this((FixtureScanner) null);
    }

    public BeanFactory(FixtureScanner scanner) {
        this(new Gson(), scanner);
    }

    public BeanFactory(Gson objectMapper) {
        this(objectMapper, null);
    }

    public BeanFactory(Gson gson, FixtureScanner scanner) {
        this.gson = gson;
        this.scanner = scanner;
        this.reader = new DefaultFixtureReader(gson);
    }

    public void init() throws IOException {
        if (scanner != null) {
            List<Resource> resources = scanner.collectResources();
            for (Resource resource : resources) {
                registerAll(reader.read(resource));
            }
        }
    }

    public void registerFixture(String name, JsonElement fixture) {
        checkNotNull(name, "Name must not be null");
        checkNotNull(fixture, "Fixture must not be null");
        fixtures.put(name, fixture);
    }

    public void registerAll(Map<String, JsonElement> fixtures) {
        checkNotNull(fixtures, "Fixtures must not be null");
        for (Map.Entry<String, JsonElement> entry : fixtures.entrySet()) {
            registerFixture(entry.getKey(), entry.getValue());
        }
    }

    public void unregisterFixture(String name) {
        fixtures.invalidate(name);
    }

    public void unregisterAll(Collection<String> names) {
        for (String name : names) {
            fixtures.invalidate(name);
        }
    }

    public <T> T create(
            Class<T> clazz,
            String... fixtureNames) throws IllegalAccessException, InstantiationException, JsonProcessingException {

        if (fixtureNames.length == 0) {
            return clazz.newInstance();
        }

        JsonElement result = mergeFixtures(fixtureNames);
        return gson.fromJson(result, clazz);
    }

    public <T> T create(JavaType type, String... fixtureNames) throws IOException {
      checkArgument(fixtureNames.length > 0, "At least one fixture needs to be specified.");
      String mergedString = createAsString(fixtureNames);
      return gson.fromJson(mergedString, type);
    }

    public String createAsString(String... fixtureNames) throws JsonProcessingException {
        return createAsString(false, newArrayList(fixtureNames));
    }

    public String createAsString(boolean pretty, List<String> fixtureNames) throws JsonProcessingException {
        if (fixtureNames.size() == 0) {
            return "{}";
        }

        JsonElement result = mergeFixtures(fixtureNames.toArray(new String[fixtureNames.size()]));
        return result.toString();
    }

    private JsonElement mergeFixtures(String[] fixturesNames) {
        List<JsonElement> fixtureList = collectFixtures(fixturesNames);
        JsonElement result = deepCopy(fixtureList.remove(0));
        for (JsonElement node : fixtureList) {
            merge(result, node);
        }
        return result;
    }

    private static JsonElement deepCopy(JsonElement element) {
        JsonElement copy = null;
        if (element.isJsonObject()) {
            JsonObject result = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                result.add(entry.getKey(), deepCopy(entry.getValue()));
            }
            copy = result;
        } else if (element.isJsonArray()) {
            JsonArray result = new JsonArray();
            for (JsonElement el : element.getAsJsonArray()) {
                result.add(deepCopy(el));
            }
            copy = result;
        } else if (element.isJsonPrimitive()) {
            copy = element;
        } else if (element.isJsonNull()) {
            copy = JsonNull.INSTANCE;
        }
        return copy;
    }

    private List<JsonElement> collectFixtures(String[] fixturesNames) {
        List<JsonElement> fixtureList = newArrayList();

        for (String fixturesName : fixturesNames) {
            JsonElement fixture = fixtures.getIfPresent(fixturesName);
            checkNotNull(fixture, fixturesName + " is not a valid fixture");
            fixtureList.add(fixture);
        }

        return fixtureList;
    }

    private static JsonElement merge(JsonElement targetNode, JsonElement sourceNode) {
        Set<Map.Entry<String, JsonElement>> fieldNames = sourceNode.getAsJsonObject().entrySet();
        fieldNames.forEach(entry -> {
            String fieldName = entry.getKey();
            JsonElement jsonElement = targetNode.getAsJsonObject().get(fieldName);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                merge(jsonElement, sourceNode.getAsJsonObject().get(fieldName));
            } else {
                if (targetNode.isJsonObject()) {
                    JsonElement value = sourceNode.getAsJsonObject().get(fieldName);
                    ((JsonObject) targetNode).add(fieldName, value);
                }
            }
        });
        return targetNode;
    }

}
