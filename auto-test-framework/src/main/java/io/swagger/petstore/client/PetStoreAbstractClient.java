package io.swagger.petstore.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.swagger.petstore.client.constant.Header;
import io.swagger.petstore.client.constant.Path;

public abstract class PetStoreAbstractClient {

    private static final String PETSTORE_API_KEY = System.getProperty("petstoreApiKey");
    private static final String PETSTORE_BASE_URI = System.getProperty("petstoreBaseUri");

    protected RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(PETSTORE_BASE_URI)
            .setBasePath(Path.BASE_PATH)
            .addHeader(Header.API_KEY, PETSTORE_API_KEY)
            .build();
}
