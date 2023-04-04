package com.swagger.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdminData {
    ADMIN_USER_NAME("admin"),
    ADMIN_PASSWORD("admin123");

    private  String  value;
}
