package com.swagger.api.controller;


import com.swagger.petstore.models.Order;
import io.restassured.response.Response;

public class StoreController extends BaseController {

    public Response createOrder(Order orderDto){
        return petStoreApiClient("/store")
                .body(orderDto)
                .post("/order");
    }
}