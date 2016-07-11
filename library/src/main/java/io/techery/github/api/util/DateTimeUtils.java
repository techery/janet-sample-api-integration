package io.techery.github.api.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtils {

    public static final String DEFAULT_ISO_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_ISO_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DEFAULT_ISO_FORMAT_DATE_ONLY = "yyyy-MM-dd";

    private DateTimeUtils() {
    }

    public static DateFormat[] getISO1DateFormats() {
        return new DateFormat[]{
                new SimpleDateFormat(DEFAULT_ISO_FORMAT_WITH_TIMEZONE, Locale.getDefault()),
                new SimpleDateFormat(DEFAULT_ISO_FORMAT, Locale.getDefault()),
                new SimpleDateFormat(DEFAULT_ISO_FORMAT_DATE_ONLY, Locale.getDefault()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZ", Locale.getDefault()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.getDefault()),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss.SSS'Z'", Locale.getDefault()),
        };
    }

    public static String convertDateToString(String pattern, Date date) {
        return DateTimeFormat.forPattern(pattern)
                .withLocale(Locale.US)
                .withZone(DateTimeZone.UTC)
                .print(new DateTime(date));
    }


}
