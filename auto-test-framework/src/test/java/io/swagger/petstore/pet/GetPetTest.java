package io.swagger.petstore.pet;

import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import io.swagger.petstore.model.Pet;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static io.swagger.petstore.model.constant.PetStatus.PENDING;
import static io.swagger.petstore.model.constant.PetStatus.SOLD;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPetTest extends BaseTest {

    private Pet pendingPet1;
    private Pet pendingPet2;
    private Pet soldPet;

    @BeforeClass
    public void createPets() {
        Pet pendingPet1ToCreate = getGenericPetWithAllFields();
        pendingPet1ToCreate.setStatus(PENDING);

        pendingPet1 = PetClient.createPet(pendingPet1ToCreate)
                .assertThat().statusCode(200)
                .extract().body().as(Pet.class);

        assertThat(pendingPet1).isNotNull();

        Pet pendingPet2ToCreate = getGenericPetWithAllFields();
        pendingPet2ToCreate.setStatus(PENDING);

        pendingPet2 = PetClient.createPet(pendingPet2ToCreate)
                .assertThat().statusCode(200)
                .extract().body().as(Pet.class);

        assertThat(pendingPet2).isNotNull();

        Pet soldPetToCreate = getGenericPetWithAllFields();
        soldPetToCreate.setStatus(SOLD);

        soldPet = PetClient.createPet(soldPetToCreate)
                .assertThat().statusCode(200)
                .extract().body().as(Pet.class);

        assertThat(soldPet).isNotNull();
    }

    @AfterClass
    public void deletePets() {
        PetClient.deletePet(pendingPet1.getId());
        PetClient.deletePet(pendingPet2.getId());
        PetClient.deletePet(soldPet.getId());
    }

    @Test
    public void testGetPet() {
        Pet fetchedPet = PetClient.getPet(pendingPet1.getId())
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().as(Pet.class);

        assertThat(fetchedPet)
                .isNotNull()
                .isEqualTo(pendingPet1);
    }

    @Test
    public void testGetPets() {
        List<Pet> fetchedPets = PetClient.getPets(PENDING)
                .assertThat()
                .statusCode(200)
                .contentType(JSON)
                .extract().jsonPath().getList(".", Pet.class);

        assertThat(fetchedPets)
                .isNotNull()
                .isNotEmpty()
                .contains(pendingPet1, pendingPet2)
                .doesNotContain(soldPet);
    }
}
