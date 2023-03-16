package com.swagger;

import com.github.javafaker.Faker;
import com.swagger.api.controller.PetController;
import com.swagger.api.controller.PetsController;
import com.swagger.petstore.models.Pet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;

public class PetCreationTests {

    static {
        requestSpecification  = new RequestSpecBuilder().log(LogDetail.ALL).build();
    }
    /**       Static initializers aren't inherited and are only executed once when the class is
     *       loaded and initialized by the JRE. That means this static block will
     *          be initialized only once irrespective of how many objects you have created out of this class.
     I am not a big fan of it and i am sure there are better alternatives for it depending
     on the situation.
     */
    Faker faker = new Faker();
    PetController petController = new PetController();
    @Test
    @DisplayName("Creation of a new pet via API")
    void creationOfANewPetViaAPI() {
        String targetPetName = faker.name().name();

        //PetDto targetPet = PetDto.builder().name(targetPetName).id(faker.number().randomNumber()).build();
        Pet targetPet = new Pet().name(targetPetName).id(faker.number().randomNumber());

        var createdPetResponse = petController
                .addNewPetToStore(targetPet);
        Assertions.assertEquals(200, createdPetResponse.statusCode());


        var petByIdResponse = petController.getPetById(targetPet.getId());
        Pet actualPet = petByIdResponse.as(Pet.class);

        Assertions.assertEquals(targetPetName, actualPet.getName());
        Assertions.assertEquals(200, petByIdResponse.statusCode());
    }
    @Test
    @DisplayName("Creation of a new pet via API and check on existing list with filter")
    void creationOfANewPetViaAPI2() {
        PetsController petsController = new PetsController();
        var createdPetResponse = new PetsController().createNewPet();
        Assertions.assertEquals(200, createdPetResponse.statusCode());

        Response availablePetsResponse = petsController.filterPetsByStatus("Available");
        Assertions.assertEquals(200, availablePetsResponse.statusCode());
    }


}
