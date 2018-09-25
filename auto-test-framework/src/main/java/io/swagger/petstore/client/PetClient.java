package io.swagger.petstore.client;

import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.client.constant.FormParam;
import io.swagger.petstore.client.constant.QueryParam;
import io.swagger.petstore.model.Pet;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.swagger.petstore.client.constant.Path.FIND_BY_STATUS;
import static io.swagger.petstore.client.constant.Path.PET;
import static io.swagger.petstore.client.constant.Path.UPLOAD_IMAGE;
import static io.swagger.petstore.client.constant.PathParam.PET_ID;

public class PetClient extends PetStoreAbstractClient {

    protected static ValidatableResponse createPet(Pet pet) {

        return given(SPECIFICATION)
                .body(pet)
                .when()
                .post(PET)
                .then();
    }

    protected static ValidatableResponse updatePet(Pet pet) {

        return given(SPECIFICATION)
                .body(pet)
                .when()
                .put(PET)
                .then();
    }

    protected static ValidatableResponse updatePet(String name, String status, int id) {

        return given(SPECIFICATION)
                .pathParam(PET_ID, id)
                .formParam(FormParam.NAME, name)
                .formParam(FormParam.STATUS, status)
                .when()
                .post(PET + "/{" + PET_ID + "}")
                .then();
    }

    protected static ValidatableResponse uploadPetImage(String additionalMetadata, String file, int id) {

        return given(SPECIFICATION)
                .pathParam(PET_ID, id)
                .multiPart(new File(file))
                .formParam(FormParam.ADDITIONAL_METADATA, additionalMetadata)
                .when()
                .post(PET + "/{" + PET_ID + "}" + UPLOAD_IMAGE)
                .then();
    }

    protected static ValidatableResponse getPet(Pet pet, int id) {

        return given(SPECIFICATION)
                .body(pet)
                .pathParam(PET_ID, id)
                .when()
                .get(PET + "/{" + PET_ID + "}")
                .then();
    }

    protected static ValidatableResponse getPets(Pet pet, String status) {

        return given(SPECIFICATION)
                .body(pet)
                .pathParam(QueryParam.STATUS, status)
                .when()
                .get(PET + FIND_BY_STATUS)
                .then();
    }

    protected static ValidatableResponse deletePet(Pet pet, int id) {

        return given(SPECIFICATION)
                .body(pet)
                .pathParam(PET_ID, id)
                .when()
                .delete(PET + "/{" + PET_ID + "}")
                .then();
    }
}
