package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.controller.userControllers.UserController;
import com.swagger.api.data.UserDataGen;
import com.swagger.petstore.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static io.restassured.RestAssured.requestSpecification;

@ExtendWith(ReportPortalExtension.class)
public class UserLogOutTests {

    static {
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
                .build();
    }

    UserController userController = new UserController();
    UserDataGen userData = new UserDataGen();


    @Test
    @DisplayName("Log out User")
    void logoutUser() {
        /* Create new User with API call */
        User targetUser = userData
                .generateDataToCreateUser();

        var createUserResponse = userController
                .createNewUserAuth(targetUser);
        Assertions.assertEquals(200, createUserResponse.statusCode());

        /* Log in this new User to system */
        var sessionIdMessage = userController
                .getUserSessionId(targetUser.getUsername(), targetUser.getPassword());
        userController
                .logOutWithValidSessionId("dvczvcdxc");
    }
}

