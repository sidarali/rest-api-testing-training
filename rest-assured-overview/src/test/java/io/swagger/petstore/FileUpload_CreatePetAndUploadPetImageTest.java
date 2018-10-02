package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.swagger.petstore.base.BaseTest;
import org.testng.annotations.Test;

import java.io.File;

public class FileUpload_CreatePetAndUploadPetImageTest extends BaseTest {

    private File pet = new File("src/test/resources/pet.json");
    private File bugImage = new File("src/test/resources/bug.png");

    @Test
    public void testUploadPetImage_Multipart() {

        int petId = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().path("id");

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .multiPart("file", bugImage)
                .formParam("additionalMetadata", "bug image")
                .when()
                .post("https://petstore.swagger.io/v2/pet/" + petId + "/uploadImage")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    //image/png media type is not supported by the service
    @Test(enabled = false)
    public void testUploadPetImage_Direct() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType("image/png")
                .body(bugImage)
                .when()
                .post("https://petstore.swagger.io/v2/pet/91/uploadImage")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
