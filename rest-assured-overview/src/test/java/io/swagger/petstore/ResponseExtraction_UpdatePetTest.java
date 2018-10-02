package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.Tag;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseExtraction_UpdatePetTest extends BaseTest {

    private int petId = 91;

    private String updatedPetJson = "{" +
            "\"id\":" + petId + "," +
            "\"category\":{" +
            "\"id\":2," +
            "\"name\":\"auto_test_sample_category_updated\"" +
            "}," +
            "\"name\":\"auto_test_sample_pet_updated\"," +
            "\"photoUrls\":[" +
            "\"https://auto-test-sample-photo1-updated\"" + "," +
            "\"https://auto-test-sample-photo2-updated\"" +
            "]," +
            "\"tags\":[" +
            "{" +
            "\"id\":2," +
            "\"name\":\"auto_test_sample_tag_updated\"" +
            "}" +
            "]," +
            "\"status\":\"pending\"" +
            "}";

    @Test
    public void testUpdatePet() {
        ValidatableResponse response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(updatedPetJson)
                .when()
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        String responseBody = response.extract().body().asString();
        assertThat(responseBody).isEqualTo(updatedPetJson);

        String status = response.extract().body().path("status");
        assertThat(status).isEqualTo("pending");

        int id = response.extract().body().path("id");
        assertThat(id).isEqualTo(91);

        List<String> photoUrls = response.extract().body().jsonPath().getList("photoUrls");
        assertThat(photoUrls)
                .isNotNull()
                .hasSize(2)
                .contains("https://auto-test-sample-photo1-updated", "https://auto-test-sample-photo2-updated");

        Pet pet = response.extract().body().as(Pet.class);
        assertThat(pet).isNotNull();
        assertThat(pet.getName()).isEqualTo("auto_test_sample_pet_updated");
        assertThat(pet.getStatus()).isEqualTo("pending");

        Category category = response.extract().body().jsonPath().getObject("category", Category.class);
        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo("auto_test_sample_category_updated");

        List<Tag> tags = response.extract().body().jsonPath().getList("tags", Tag.class);
        assertThat(tags).isNotNull().hasSize(1);
        assertThat(tags.get(0).getName()).isEqualTo("auto_test_sample_tag_updated");
    }
}
