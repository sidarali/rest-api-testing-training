package io.swagger.petstore.pet;

import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.Tag;
import io.swagger.petstore.model.builder.PetBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static io.swagger.petstore.data.PetStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePetTest extends BaseTest {

    private Pet pet;
    private static final String UPDATED_NAME = "pet_updated_name";

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
    public void testUpdatePet() {
        List<String> photoUrlsUpdated = new ArrayList<>();
        photoUrlsUpdated.add("photos.org/updated-photo1");
        photoUrlsUpdated.add("photos.org/updated-photo2");
        photoUrlsUpdated.add("photos.org/updated-photo3");

        List<Tag> tagsUpdated = new ArrayList<>();
        tagsUpdated.add(new Tag(1, "Test tag 1 updated"));
        tagsUpdated.add(new Tag(2, "Test tag 2 updated"));
        tagsUpdated.add(new Tag(3, "Test tag 3 updated"));

        Pet petToUpdate = new PetBuilder()
                .setId(pet.getId())
                .setName(UPDATED_NAME)
                .setCategory(new Category(1, "Test category 1 updated"))
                .setPhotoUrls(photoUrlsUpdated)
                .setTags(tagsUpdated)
                .setStatus(PENDING)
                .build();

        Pet updatedPet = PetClient.updatePet(petToUpdate)
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().as(Pet.class);

        assertThat(updatedPet)
                .isNotNull()
                .isEqualTo(petToUpdate);
    }

    @Test
    public void testUpdatePet_FormData() {
        Pet updatedPet = new PetBuilder()
                .setId(pet.getId())
                .setName(UPDATED_NAME)
                .setCategory(pet.getCategory())
                .setPhotoUrls(pet.getPhotoUrls())
                .setTags(pet.getTags())
                .setStatus(PENDING)
                .build();

        String responseBody = PetClient.updatePet(UPDATED_NAME, PENDING, pet.getId())
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().asString();

        assertThat(responseBody)
                .isNotNull()
                .isEqualTo("");

        Pet fetchedPet = PetClient.getPet(pet.getId())
                .assertThat().statusCode(200)
                .extract().body().as(Pet.class);

        assertThat(fetchedPet)
                .isNotNull()
                .isEqualTo(updatedPet);
    }
}
