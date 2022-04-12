package ru.hh.school.utils;

import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DefaultInstantSerializer extends InstantSerializer {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXX").withZone(ZoneId.systemDefault());

    public DefaultInstantSerializer() {
        super(InstantSerializer.INSTANCE, false, FORMATTER);
    }

}
