package com.swagger.api.controller.petControllers;

import com.swagger.api.controller.BaseController;
import com.swagger.petstore.models.Pet;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetController extends BaseController {

    public Response addNewPetToStore(Pet petDto) {
        return petApi()
                .body(petDto)
                .post();

    }
    public Response getPetById(long targetPetId){
        return petApi().get("/{targetPetId}", targetPetId);
    }
    private RequestSpecification petApi(){
        return petStoreApiClient("/pet");
    }

}