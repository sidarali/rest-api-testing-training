package io.swagger.petstore.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.Tag;
import io.swagger.petstore.data.PetStatus;
import io.swagger.petstore.model.registry.PetRegistry;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseTest {

    protected static final String IMAGE_FILE_NAME = "bug.png";
    protected long nonexistent_id = ThreadLocalRandom.current().nextInt(1000, 2000);

    @BeforeClass
    public void setCommonRestAssuredConfigs() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                //enable for local development purposes
//                .addFilter(new RequestLoggingFilter())
//                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    //helper methods

    //creation methods
    protected Pet getGenericPetWithAllFields() {
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("photos.org/photo1");
        photoUrls.add("photos.org/photo2");

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "Test tag 1"));
        tags.add(new Tag(2, "Test tag 2"));

        return PetRegistry.getPetBuilder()
                .setCategory(new Category(1, "Test category 1"))
                .setPhotoUrls(photoUrls)
                .setTags(tags)
                .setStatus(PetStatus.AVAILABLE)
                .build();
    }
}
