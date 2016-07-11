package io.techery.github.api.api_common.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SmartEnumTypeAdapterFactory implements TypeAdapterFactory {
    private String fallbackKey;

    public SmartEnumTypeAdapterFactory(String fallbackKey) {
        this.fallbackKey = SmartEnumTypeAdapter.toLowercase(fallbackKey);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? extends T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }

        return (TypeAdapter<T>) new SmartEnumTypeAdapter(rawType, fallbackKey);
    }

    public static class SmartEnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {

        protected final Map<String, T> keyToEnum = new HashMap<String, T>();
        protected final Map<T, String> enumToKey = new HashMap<T, String>();

        protected final String fallbackKey;

        public SmartEnumTypeAdapter(Class<T> classOfT, String fallbackKey) {
            this.fallbackKey = fallbackKey;

            try {
                for (T constant : classOfT.getEnumConstants()) {
                    String name = constant.name();
                    SerializedName annotation = classOfT.getField(name).getAnnotation(SerializedName.class);
                    if (annotation != null) {
                        name = annotation.value();
                        keyToEnum.put(name, constant);
                        enumToKey.put(constant, name);
                        for (String alternate : annotation.alternate()) {
                            keyToEnum.put(alternate, constant);
                            enumToKey.put(constant, alternate);
                        }
                    } else {
                        enumToKey.put(constant, name.toLowerCase());
                    }
                    keyToEnum.put(name.toLowerCase(), constant);
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException("Missing field in " + classOfT.getName(), e);
            }
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(enumToKey.get(value));
            }
        }

        @Override
        public T read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            } else {
                T t = keyToEnum.get(reader.nextString());
                return t != null ? t : keyToEnum.get(fallbackKey);
            }
        }

        private static String toLowercase(Object o) {
            return o.toString().toLowerCase(Locale.US);
        }

    }
}
