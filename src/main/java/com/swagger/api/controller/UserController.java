package com.swagger.api.controller;

import com.swagger.petstore.models.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.epam.reportportal.annotations.Step;

public class UserController extends BaseController {

    public Response createNewUser(User userDto) {
        return userApi()
                .body(userDto)
                .post();
    }

    @Step("Get user by userName")
    public Response getUserByName(String targetUserName){
        return userApi().get("/{username}", targetUserName);
    }
    private RequestSpecification userApi(){
        return petStoreApiClient("/user");
    }
    @Step("Log in existing User with valid creds")
    public Response logInUserWithValidCreds(String targetUserName, String targetPass){
        Response response = userApi().auth()
                .preemptive()
                .basic(targetUserName, targetPass)
                .get("/login");
        System.out.println("RESPONSE logInUserWithValidCreds: " + response.getStatusLine());
        return response;

    }
    @Step("Delete User by userName")
    public Response deleteUserByUsername(String targetUserName){
        Response response = userApi()
                .delete("/{username}", targetUserName);
        System.out.println("RESPONSE deleteUserByUsername: " + response.getStatusLine());
        return response;
    }

}