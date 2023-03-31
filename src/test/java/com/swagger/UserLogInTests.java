package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.github.javafaker.Faker;
import com.swagger.api.controller.UserController;
import com.swagger.api.data.UserDataGen;
import com.swagger.petstore.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static io.restassured.RestAssured.requestSpecification;

public class UserLogInTests {

  static {requestSpecification = new RequestSpecBuilder()
          .log(LogDetail.ALL)
          .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
          .build();
  }
    Faker faker = new Faker();
    UserController userController = new UserController();
    UserDataGen userData = new UserDataGen();
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Log in User with valid credentials")
    void loginUserWithValidCreds() {
 /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();

        var createUserResponse = userController
                .createNewUser(targetUser);
        Assertions.assertEquals(200,createUserResponse.statusCode());

/* Log in this new User to system */
        var loginResponse = userController
                .logInUserWithValidCreds(targetUser.getUsername(), targetUser.getPassword());
        Assertions.assertEquals(200, loginResponse.getStatusCode());

/* Delete User after test passed */
        Response userDeleted = userController.deleteUserByUsername(targetUser.getUsername());
        Assertions.assertEquals(200, userDeleted.statusCode());
    }
}

