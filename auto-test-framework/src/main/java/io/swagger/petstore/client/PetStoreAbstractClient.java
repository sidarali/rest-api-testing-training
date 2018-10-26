package io.swagger.petstore.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import io.swagger.petstore.client.constant.Header;
import io.swagger.petstore.client.constant.Path;

import static io.restassured.http.ContentType.JSON;

abstract class PetStoreAbstractClient {

    private static final String PETSTORE_API_KEY = System.getProperty("petstoreApiKey");
    private static final String PETSTORE_BASE_URI = System.getProperty("petstoreBaseUri");

    private static final LogConfig LOG_CONFIG = new LogConfig()
            .enableLoggingOfRequestAndResponseIfValidationFails();

    protected static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .addFilter(new AllureRestAssured())
            .setBaseUri(PETSTORE_BASE_URI)
            .setBasePath(Path.BASE_PATH)
            .setConfig(RestAssuredConfig.config().logConfig(LOG_CONFIG))
            .build();
}
