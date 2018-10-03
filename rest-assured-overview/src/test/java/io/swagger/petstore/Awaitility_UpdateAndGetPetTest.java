package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.swagger.petstore.base.BaseTest;
import org.awaitility.Duration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

public class Awaitility_UpdateAndGetPetTest extends BaseTest {

    @BeforeClass
    public void createPet() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(new File("src/test/resources/pet.json"))
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdatePet() {
        int petId = 91;

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .formParam("name", "auto_test_sample_pet_updated")
                .formParam("status", "pending")
                .pathParam("id", 91)
                .when()
                .post("https://petstore.swagger.io/v2/pet/{id}")
                .then()
                .statusCode(200);

        await("updated pet status")
                .atMost(Duration.FIVE_SECONDS)
                .with()
                .pollDelay(20, MILLISECONDS)
                .pollInterval(Duration.FIVE_HUNDRED_MILLISECONDS)
                .until(petStatus(petId), equalTo("pending"));
    }

    private Callable<String> petStatus(int petId) {
        return new Callable<String>() {
            public String call() throws Exception {
                return RestAssured.given()
                        .header("api_key", "1234567890")
                        .contentType(ContentType.JSON)
                        .when()
                        .get("https://petstore.swagger.io/v2/pet/" + petId)
                        .then()
                        .extract().body().path("status");
            }
        };
    }
}
