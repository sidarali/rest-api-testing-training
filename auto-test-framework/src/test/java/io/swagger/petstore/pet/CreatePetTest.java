package io.swagger.petstore.pet;

import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.Tag;
import io.swagger.petstore.model.registry.PetRegistry;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.swagger.petstore.model.constant.PetStatus.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class CreatePetTest extends BaseTest {

    private int petId;

    @AfterMethod
    public void deletePet() {

        PetClient.deletePet(petId);
    }

    @DataProvider(name = "status")
    public static Object[][] createData() {
        return new Object[][]{
                {AVAILABLE}, {PENDING}, {SOLD}
        };
    }

    @Test(dataProvider = "status")
    public void testCreatePet_AllFields_Status(String status) {

        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("photos.org/photo1");
        photoUrls.add("photos.org/photo2");

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "Test tag 1"));
        tags.add(new Tag(2, "Test tag 2"));

        Pet petToCreate = PetRegistry.getPetBuilder()
                .setCategory(new Category(1, "Test category 1"))
                .setPhotoUrls(photoUrls)
                .setTags(tags)
                .setStatus(status)
                .build();

        testCreatePet(petToCreate);
    }

    @Test
    public void testCreatePet_OnlyRequiredFields() {

        Pet petToCreate = PetRegistry.getUniquePetWithSetRequiredFields();
        testCreatePet(petToCreate);
    }

    private void testCreatePet(Pet petToCreate) {

        petId = petToCreate.getId();

        Pet createdPet = PetClient.createPet(petToCreate)
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().as(Pet.class);

        assertThat(createdPet)
                .isNotNull()
                .isEqualTo(petToCreate);

        Pet fetchedPet = PetClient.getPet(petId)
                .assertThat().statusCode(200)
                .extract().body().as(Pet.class);

        assertThat(fetchedPet)
                .isNotNull()
                .isEqualTo(petToCreate);
    }
}
