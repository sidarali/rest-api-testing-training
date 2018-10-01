package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class Logging_CreatePetTest {

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

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
//                .log().ifValidationFails(LogDetail.BODY)
                .log().headers()
                .log().method()
                .log().uri()
                .log().body()
//                .log().everything()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
//                .log().ifValidationFails(LogDetail.STATUS)
                .log().status()
                .log().headers()
                .log().body()
//                .log().everything()
                .statusCode(200);
    }
}
