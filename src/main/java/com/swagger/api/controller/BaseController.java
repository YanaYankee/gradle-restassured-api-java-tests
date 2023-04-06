package com.swagger.api.controller;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseController { // abstract but no abstract methods
    // just to emphasise it
    public static RequestSpecification petStoreApiClient(String basePath) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath(basePath)
                .log().everything()
                .contentType(ContentType.JSON);
    }

    public static String getJsonPath(Response response, String key) {
        String complete = response.asString();
        JsonPath js = new JsonPath(complete);
        return js.get(key).toString();
    }

    public static String makeFirstLetterUpperCase(String toEdit) {
        String resultingWording = toEdit.substring(0, 1).toUpperCase()
                + toEdit.substring(1);
        System.out.println("logInUserWithMistypedUserName: " + resultingWording);
        return resultingWording;
    }
}