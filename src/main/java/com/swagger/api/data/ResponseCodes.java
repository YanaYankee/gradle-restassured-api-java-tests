package com.swagger.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter

public enum ResponseCodes {
        OK(200, "HTTP/1.1 200 OK"),
        INVALID(400, "HTTP/1.1 400 Invalid"),
        NOT_FOUND(404,"HTTP/1.1 404 Not Found"),
        NOT_ALLOWED(405,"HTTP/1.1 405 Method Not Allowed"),
        NOT_ACCEPTABLE(406,"HTTP 406 Not Acceptable");
        private  int status;
        private  String  statusLine;
}
