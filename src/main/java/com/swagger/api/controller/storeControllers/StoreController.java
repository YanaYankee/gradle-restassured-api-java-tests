package com.swagger.api.controller.storeControllers;


import com.swagger.api.controller.BaseController;
import com.swagger.petstore.models.Order;
import io.restassured.response.Response;

public class StoreController extends BaseController {

    public Response createOrder(Order orderDto) {
        return petStoreApiClient("/store")
                .body(orderDto)
                .post("/order");
    }
}