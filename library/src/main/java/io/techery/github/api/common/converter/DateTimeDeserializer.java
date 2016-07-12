package io.techery.github.api.common.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import io.techery.github.api.util.DateTimeUtils;

public class DateTimeDeserializer implements JsonDeserializer<Date> {

    private DateFormat[] dateFormats;

    public DateTimeDeserializer() {
        dateFormats = DateTimeUtils.getISO1DateFormats();
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        for (DateFormat format : dateFormats) {
            try {
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = format.parse(json.getAsString());
                return date;
            } catch (ParseException e) {
            }
        }
        return null;
    }

}
