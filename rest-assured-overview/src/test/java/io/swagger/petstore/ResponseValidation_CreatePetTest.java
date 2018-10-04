package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.swagger.petstore.base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class ResponseValidation_CreatePetTest extends BaseTest {

    private int petId = 91;

    private String petJson = "{" +
            "\"id\":" + petId + "," +
            "\"category\":{" +
            "\"id\":1," +
            "\"name\":\"auto_test_sample_category\"" +
            "}," +
            "\"name\":\"auto_test_sample_pet\"," +
            "\"photoUrls\":[" +
            "\"https://auto-test-sample-photo\"" +
            "]," +
            "\"tags\":[" +
            "{" +
            "\"id\":1," +
            "\"name\":\"auto_test_sample_tag\"" +
            "}" +
            "]," +
            "\"status\":\"available\"" +
            "}";

    @Test
    public void testCreatePet() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Date", notNullValue())
                .body("id", equalTo(petId),
                        "name", equalTo("auto_test_sample_pet"),
                        "category.id", equalTo(1),
                        "category.name", equalTo("auto_test_sample_category"),
                        "photoUrls", hasSize(1),
                        "photoUrls", hasItem("https://auto-test-sample-photo"),
                        "tags.id", hasSize(1),
                        "tags.id", hasItem(1),
                        "tags.name", hasSize(1),
                        "tags.name", hasItem("auto_test_sample_tag"),
                        "status", equalTo("available"));
    }
}
