package io.swagger.petstore;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.swagger.petstore.base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class SpecificationReUse_CRUDPetTest extends BaseTest {

    private String petId = "91";

    private String petJsonToCreate = "{" +
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

    private String petJsonToUpdate = "{" +
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

    private RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("api_key", "1234567890")
            .setAccept(JSON)
            .setBaseUri("https://petstore.swagger.io")
            .setBasePath("/v2/pet")
            .build();

    private ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(JSON)
            .build();

    @Test
    public void testCreatePet() {

        String createdPetJson = given()
                .spec(requestSpecification)
                .contentType(JSON)
                .body(petJsonToCreate)
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .extract().body().asString();

        assertThat(createdPetJson).isEqualTo(petJsonToCreate);

        String updatedPetJson = given()
                .spec(requestSpecification)
                .contentType(JSON)
                .body(petJsonToUpdate)
                .when()
                .put()
                .then()
                .spec(responseSpecification)
                .extract().body().asString();

        assertThat(updatedPetJson).isEqualTo(petJsonToUpdate);

        String fetchedPetJson = given()
                .spec(requestSpecification)
                .when()
                .get(petId)
                .then()
                .spec(responseSpecification)
                .extract().body().asString();

        assertThat(fetchedPetJson).isEqualTo(updatedPetJson);

        String deletePetResponseBody = given()
                .spec(requestSpecification)
                .when()
                .delete(petId)
                .then()
                .spec(responseSpecification)
                .extract().body().asString();

        assertThat(deletePetResponseBody).isEqualTo("");
    }
}
