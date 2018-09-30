package io.swagger.petstore.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.client.constant.FormParam;
import io.swagger.petstore.client.constant.QueryParam;
import io.swagger.petstore.model.Pet;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;
import static io.swagger.petstore.client.constant.Path.FIND_BY_STATUS;
import static io.swagger.petstore.client.constant.Path.PET;
import static io.swagger.petstore.client.constant.Path.UPLOAD_IMAGE;
import static io.swagger.petstore.client.constant.PathParam.PET_ID;

public class PetClient extends PetStoreAbstractClient {

    private static final String FILE_LOCATION = "src/test/resources/";

    @Step
    public static ValidatableResponse createPet(Pet pet) {

        return given(SPECIFICATION)
                .contentType(JSON)
                .body(pet)
                .when()
                .post(PET)
                .then();
    }

    @Step
    public static ValidatableResponse updatePet(Pet pet) {

        return given(SPECIFICATION)
                .contentType(JSON)
                .body(pet)
                .when()
                .put(PET)
                .then();
    }

    @Step
    public static ValidatableResponse updatePet(String name, String status, long id) {

        return given(SPECIFICATION)
                .contentType(URLENC)
                .formParam(FormParam.NAME, name)
                .formParam(FormParam.STATUS, status)
                .pathParam(PET_ID, id)
                .when()
                .post(PET + "/{" + PET_ID + "}")
                .then();
    }

    @Step
    public static ValidatableResponse uploadPetImage(String additionalMetadata, String file, long id) {

        return given(SPECIFICATION)
                .multiPart(new File(FILE_LOCATION + file))
                .formParam(FormParam.ADDITIONAL_METADATA, additionalMetadata)
                .pathParam(PET_ID, id)
                .when()
                .post(PET + "/{" + PET_ID + "}" + UPLOAD_IMAGE)
                .then();
    }

    @Step
    public static ValidatableResponse getPet(long id) {

        return given(SPECIFICATION)
                .pathParam(PET_ID, id)
                .when()
                .get(PET + "/{" + PET_ID + "}")
                .then();
    }

    @Step
    public static ValidatableResponse getPets(String status) {

        return given(SPECIFICATION)
                .queryParam(QueryParam.STATUS, status)
                .when()
                .get(PET + FIND_BY_STATUS)
                .then();
    }

    @Step
    public static ValidatableResponse deletePet(long id) {

        return given(SPECIFICATION)
                .pathParam(PET_ID, id)
                .when()
                .delete(PET + "/{" + PET_ID + "}")
                .then();
    }
}
