package io.swagger.petstore.pet;

import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.registry.PetRegistry;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.swagger.petstore.data.PetStatus.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class CreatePetTest extends BaseTest {

    private long petId;

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
        Pet petToCreate = getGenericPetWithAllFields();
        petToCreate.setStatus(status);

        testCreatePet(petToCreate);
    }

    @Test
    public void testCreatePet_OnlyRequiredFields() {
        Pet petToCreate = PetRegistry.getUniquePetWithOnlyRequiredFields();
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
