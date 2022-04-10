package ru.hh.school.utils;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class DefaultInstantDeserializer extends InstantDeserializer<Instant> {

    //из hh приходит 2022-01-01T10:10:10+0300, InstantDeserializer до версии 2.13 не понимает такое из коробки
    //он хочет с разделителем в смещении - 2022-01-01T10:10:10+03:00
    //пришлось форматтер писать
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public DefaultInstantDeserializer() {
        super(INSTANT, FORMATTER);
    }

}
