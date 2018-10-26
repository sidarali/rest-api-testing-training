package io.swagger.petstore.school;

import io.restassured.response.Response;
import io.swagger.petstore.base.BaseTest;
import io.swagger.petstore.client.PetClient;
import io.swagger.petstore.model.ApiResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

/**
 * Created by alexander.sidorenko@ctco.lv on 10/26/18.
 */
public class GetSchoolTest extends BaseTest {

    @Test
    public void getTest() {
        String responseStr =
                given().when().
                        get("/").
                        then().extract().response().asString();
        System.out.println("and response was " + responseStr);
        assertTrue(responseStr.contains(" ci in jenkins"));
        

    }
}
