package io.swagger.petstore.basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CRUDPetTest {

    private String petId = "91";

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

    private String updatedPetJson = "{" +
            "\"id\":" + petId + "," +
            "\"category\":{" +
            "\"id\":2," +
            "\"name\":\"auto_test_sample_category_updated\"" +
            "}," +
            "\"name\":\"auto_test_sample_pet_updated\"," +
            "\"photoUrls\":[" +
            "\"https://auto-test-sample-photo-updated\"" +
            "]," +
            "\"tags\":[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"auto_test_sample_tag_updated\"" +
            "}" +
            "]," +
            "\"status\":\"available\"" +
            "}";

    @Test
    public void testCreatePet() {

        String responseBody = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .log().everything()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(responseBody).isEqualTo(petJson);
    }

    @Test
    public void testUpdatePet() {

        String responseBody = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(updatedPetJson)
                .when()
                .log().everything()
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(responseBody).isEqualTo(updatedPetJson);
    }

    @Test
    public void testGetPet() {
        String responseBody = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .when()
                .log().everything()
                .get("https://petstore.swagger.io/v2/pet/" + petId)
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(responseBody).isEqualTo(updatedPetJson);
    }

    @Test
    public void testDeletePet() {
        String responseBody = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .when()
                .log().everything()
                .delete("https://petstore.swagger.io/v2/pet/" + petId)
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(responseBody).isEqualTo("");
    }
}
