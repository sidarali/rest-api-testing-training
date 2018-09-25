package io.swagger.petstore.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.swagger.petstore.client.constant.Header;
import io.swagger.petstore.client.constant.Path;

import static io.restassured.http.ContentType.JSON;

public abstract class PetStoreAbstractClient {

    private static final String PETSTORE_API_KEY = System.getProperty("petstoreApiKey");
    private static final String PETSTORE_BASE_URI = System.getProperty("petstoreBaseUri");

    protected static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .addHeader(Header.API_KEY, PETSTORE_API_KEY)
            .setContentType(JSON)
            .setAccept(JSON)
            .setBaseUri(PETSTORE_BASE_URI)
            .setBasePath(Path.BASE_PATH)
            .build();
}
