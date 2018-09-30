package io.swagger.petstore.pet;

import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeletePetValidationTest extends BaseTest {

    @Test
    public void testDeletePet_NonexistentId() {
        String responseBody = PetClient.deletePet(nonexistent_id)
                .assertThat()
                .statusCode(404)
                .extract().body().asString();

        assertThat(responseBody)
                .isNotNull()
                .isEqualTo("");
    }
}
