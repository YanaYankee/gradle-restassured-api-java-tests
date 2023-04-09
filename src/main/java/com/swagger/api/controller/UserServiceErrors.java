package com.swagger.api.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserServiceErrors {
    UserIsDuplicated("E50001", "User is already presented in DB"),
    INVALID("400", "HTTP/1.1 400 Invalid"),
    NOT_FOUND("404", "HTTP/1.1 404 Not Found"),
    NOT_ALLOWED("405", "HTTP/1.1 405 Method Not Allowed"),
    NOT_ACCEPTABLE("406", "HTTP 406 Not Acceptable");

    private final String code, message;
}
