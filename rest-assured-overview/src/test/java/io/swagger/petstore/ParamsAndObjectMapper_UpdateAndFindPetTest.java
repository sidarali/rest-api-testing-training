package io.swagger.petstore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.model.Pet;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParamsAndObjectMapper_UpdateAndFindPetTest extends BaseTest {

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
    public void testFindPetByStatus() {

        String petsListJson = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .queryParam("status", "available")
                .when()
                .get("https://petstore.swagger.io/v2/pet/findByStatus")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(petsListJson).isNotNull();

        List<Pet> fetchedPets = extractPetsList(petsListJson);
        assertThat(fetchedPets).isNotEmpty();
        assertThat(fetchedPets.get(0).getStatus()).isEqualTo("available");
    }

    private List<Pet> extractPetsList(String petsListJson) {
        List<Pet> pets = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            pets = mapper.readValue(petsListJson, new TypeReference<List<Pet>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pets;
    }

    @Test
    public void testUpdatePet() {

        String responseBody = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .formParam("name", "auto_test_sample_pet_updated")
                .formParam("status", "pending")
                .pathParam("id", 91)
                .when()
                .post("https://petstore.swagger.io/v2/pet/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(responseBody)
                .isNotNull()
                .isEqualTo("");
    }
}
