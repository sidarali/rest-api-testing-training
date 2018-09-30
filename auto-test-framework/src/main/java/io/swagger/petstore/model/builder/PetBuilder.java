package io.swagger.petstore.model.builder;

import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.Tag;

import java.util.List;

public class PetBuilder {

    private Pet pet;

    public PetBuilder() {
        pet = new Pet();
    }

    public PetBuilder setId(long id) {
        pet.setId(id);
        return this;
    }

    public PetBuilder setName(String name) {
        pet.setName(name);
        return this;
    }

    public PetBuilder setStatus(String status) {
        pet.setStatus(status);
        return this;
    }

    public PetBuilder setCategory(Category category) {
        pet.setCategory(category);
        return this;
    }

    public PetBuilder setPhotoUrls(List<String> photoUrls) {
        pet.setPhotoUrls(photoUrls);
        return this;
    }

    public PetBuilder setTags(List<Tag> tags) {
        pet.setTags(tags);
        return this;
    }

    public Pet build() {
        return pet;
    }
}
