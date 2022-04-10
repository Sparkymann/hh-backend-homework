package ru.hh.school.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class CommonResourceUtils {

    private CommonResourceUtils() {}

    public static WebTarget getWebTarget(String url) {
        Client client = ClientBuilder.newClient();
        return client.target(url);
    }

}
