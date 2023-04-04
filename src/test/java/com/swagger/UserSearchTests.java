package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.asserts.ResponseAsserts;
import com.swagger.api.controller.UserController;
import com.swagger.api.data.UserDataGen;
import com.swagger.petstore.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static io.restassured.RestAssured.requestSpecification;

public class UserDeleteTests {

  static {requestSpecification = new RequestSpecBuilder()
          .log(LogDetail.ALL)
          .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
          .build();
  }
    UserController userCont = new UserController();
    UserDataGen userData = new UserDataGen();
    ResponseAsserts asserts = new ResponseAsserts();
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete existing User")
    void deleteExistingUser() {
 /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        var createUserResponse = userCont
                .createNewUser(targetUser);
        asserts.okAssertion(createUserResponse);
/* Delete existing User to check server response */
        Response userDeleted = userCont
                .deleteUserByUsername(targetUser.getUsername());
        asserts.okAssertion(userDeleted);
        /* Check if deleted with search for User */
        /* Step + assert should go here  */
    }
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete not existing User, 404 Not found check")
    void deleteNotExistingUser() {
/* Delete not existing user to check 404 response code*/
        Response userDeleted = userCont
                .deleteUserByUsername("ornsierfisnuveifhiseufnaivufeuivgs");
        asserts.notFoundAssertion(userDeleted);
        /* Check if deleted with search for User */
        /* Step + assert should go here  */
    }
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete User with empty username to check 405 Method not allowed")
    void deleteUserWithEmptyUsername() {
 /* Delete User fetching empty userName to check 405 response code*/
        Response userDeleted = userCont.deleteUserByUsername("");
        System.out.println("RESPONSE userDeleted: " + userDeleted.asString());
        asserts.notAllowedAssertion(userDeleted);
        /* Check if deleted with search for User */
        /* Step + assert should go here  */
    }

    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete User with invalid username")
    void deleteUserWithDigitalUserName() {
/* Delete not existing user to check 404 response code*/
        Response userDeleted = userCont.deleteUserByUsername("12123123");
        asserts.notFoundAssertion(userDeleted);
/* Check ifth search for User */
/* Step + assert should go here  */
    }
}

