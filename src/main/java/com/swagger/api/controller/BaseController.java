package com.swagger.api.controller;

import io.restassured.http.ContentType;
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
}
