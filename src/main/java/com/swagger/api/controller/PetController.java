package com.swagger.api.controller;

import com.swagger.api.model.PetDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetController extends BaseController {

    public Response addNewPetToStore(PetDto petDto) {
            return petApi()
//                .body("{\n" +
//                        "  \"id\":" + petId + ",\n" +
//                        "  \"category\": {\n" +
//                        "    \"id\": 0,\n" +
//                        "    \"name\": \"string\"\n" +
//                        "  },\n" +
//                        "  \"name\": \"" + targetPetName + "\",\n" +
//                        "  \"photoUrls\": [\n" +
//                        "    \"string\"\n" +
//                        "  ],\n" +
//                        "  \"tags\": [\n" +
//                        "    {\n" +
//                        "      \"id\": 0,\n" +
//                        "      \"name\": \"string\"\n" +
//                        "    }\n" +
//                        "  ],\n" +
//                        "  \"status\": \"available\"\n" +
//                        "}")
//                .body(getClass().getResourceAsStream("src/test/resources/dataPet.json")
//                        .toString().replaceAll("{id}"), faker.number().digit())
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
