package com.swagger.api.common;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

public class ResponseExpectMessages {
    private final Response targetResponse;

    public ResponseExpectMessages(Response targetResponse) {
        this.targetResponse = targetResponse;
    }

    public String expectedStatusesCode(StatusCode expectedStatusCode) {
        return SBB.sbb().n()
                .append("Expected status code: ").w().append(expectedStatusCode.name()).w().append(expectedStatusCode.code).n()
                .append("Actual status code: " + "").w().append(StatusCode.byValue(targetResponse.statusCode())).w().append(targetResponse.statusCode()).n()
                .append("Actual response body: ").n()
                .append(targetResponse.body().asString()).n()
                .bld();
    }

    public String expectedResponseBodyClass(Class expectedClass) {
        return SBB.sbb().n()
                .append("Unexpected response body: ").n()
                .append(targetResponse.asString()).n()
                .append("Expected body type: ").w().sQuoted(expectedClass.getSimpleName()).n()
                .append(expectedClass.getDeclaredFields())
                .bld();
    }

    @AllArgsConstructor
    public enum StatusCode {
        OK(200), INVALID(400), NOT_FOUND(404), NOT_ALLOWED(405), NOT_ACCEPTABLE(406);

        public int code;

        public static StatusCode byValue(int value) {
            return Stream.of(StatusCode.values())
                    .filter(code -> code.code == value).findFirst()
                    .orElseThrow(() -> new RuntimeException(SBB.sbb("No such status code known").w().append(value).bld()));
        }

    }
}










