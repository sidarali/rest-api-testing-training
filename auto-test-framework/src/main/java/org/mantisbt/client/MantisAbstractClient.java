package org.mantisbt.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.mantisbt.client.constant.Header;
import org.mantisbt.client.constant.Path;

import static io.restassured.http.ContentType.JSON;

public abstract class MantisAbstractClient {

    private static final String MANTIS_BASE_URI = System.getProperty("mantisBaseUri");
    private static final int MANTIS_PORT = Integer.parseInt(System.getProperty("mantisPort"));
    private static final String MANTIS_BASE_PATH = System.getProperty("mantisBasePath");

    private static final String MANTIS_AUTH_TOKEN = System.getProperty("mantisAuthToken");


    protected static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .addHeader(Header.AUTHORIZATION, MANTIS_AUTH_TOKEN)
            .setContentType(JSON)
            .setAccept(JSON)
            .setBaseUri(MANTIS_BASE_URI)
            .setPort(MANTIS_PORT)
            .setBasePath(MANTIS_BASE_PATH + Path.API_REST)
            .build();
}
