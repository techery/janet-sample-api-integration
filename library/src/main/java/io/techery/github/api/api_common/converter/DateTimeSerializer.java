package io.techery.github.api.api_common.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

import io.techery.github.api.util.DateTimeUtils;

import static io.techery.github.api.util.DateTimeUtils.DEFAULT_ISO_FORMAT_WITH_TIMEZONE;

public class DateTimeSerializer implements JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(DateTimeUtils.convertDateToString(DEFAULT_ISO_FORMAT_WITH_TIMEZONE, src));
    }
}
