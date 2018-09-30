package io.swagger.petstore.pet;

import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import io.swagger.petstore.data.ResponseType;
import io.swagger.petstore.model.ApiResponse;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.builder.ApiResponseBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.http.ContentType.JSON;
import static io.swagger.petstore.data.ResponseMessage.ADDITIONAL_METADATA;
import static io.swagger.petstore.data.ResponseMessage.FILE_UPLOADED_TO;
import static org.assertj.core.api.Assertions.assertThat;

public class UploadPetImageTest extends BaseTest {

    private Pet pet;

    @BeforeMethod
    public void createPet() {
        pet = PetClient.createPet(getGenericPetWithAllFields())
                .assertThat().statusCode(200)
                .extract().body().as(Pet.class);

        assertThat(pet).isNotNull();
    }

    @AfterMethod
    public void deletePet() {
        PetClient.deletePet(pet.getId());
    }

    @Test
    public void testUploadPetImage() {
        String metadata = "bug image";

        ApiResponse expectedApiResponse = new ApiResponseBuilder()
                .setCode(200)
                .setType(ResponseType.UNKNOWN)
                .setMessage(ADDITIONAL_METADATA + metadata + FILE_UPLOADED_TO + IMAGE_FILE_NAME + ", 35631 bytes")
                .build();

        ApiResponse apiResponse = PetClient.uploadPetImage(metadata, IMAGE_FILE_NAME, pet.getId())
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().as(ApiResponse.class);

        assertThat(apiResponse)
                .isNotNull()
                .isEqualTo(expectedApiResponse);
    }
}
