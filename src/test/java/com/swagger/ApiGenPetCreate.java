package com.swagger;

import com.github.javafaker.Faker;
import com.swagger.api.controller.PetController;
import com.swagger.petstore.apis.PetApi;
import com.swagger.petstore.models.Pet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ApiGenPetCreate {
    Faker faker = new Faker();
    PetController petController = new PetController();

    @Test
    @DisplayName("Create pet with API Code gen")
    void ApiGenCreatePet() {
        String targetPetName = faker.name().name();
        System.out.println("targetPetName = " + targetPetName);

        long targetId = faker.number().randomNumber();
        var targetPet = new Pet().name(targetPetName).id(targetId);
        new PetApi().addPet(targetPet);

        var petByIdResponse = petController.getPetById(targetPet.getId());
        Pet actualPet = petByIdResponse.as(Pet.class);
        System.out.println("actualPet.getName() = " + actualPet.getName());

        Assertions.assertEquals(targetPetName, actualPet.getName());
    }


}
