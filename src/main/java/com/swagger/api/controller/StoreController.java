package com.swagger.api.controller;

import com.swagger.api.model.OrderDto;
import io.restassured.response.Response;

public class StoreController extends BaseController {

    public Response createOrder(OrderDto orderDto){
        return petStoreApiClient("/store")
                .body(orderDto)
                .post("/order");
    }
}
