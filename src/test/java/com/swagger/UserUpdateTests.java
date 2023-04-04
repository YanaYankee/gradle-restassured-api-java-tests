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

public class UserUpdateTests {


  static {requestSpecification = new RequestSpecBuilder()
          .log(LogDetail.ALL)
          //.addFilter(new AllureRestAssured())
          .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
          .build();
  }

    ResponseAsserts asserts = new ResponseAsserts();
    UserDataGen userData = new UserDataGen();
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Creation of a new User with required data present")
    void creationOfANewUserViaApi() {
        UserController userCont = new UserController();
/* Generate data for user create and user update */
        User targetUser = userData.generateDataToCreateUser();
        User targetUserUpdated = userData.generateDataToCreateUser();
/* Create new User with API call */
        var createUserResponse = userCont
                .createNewUser(targetUser);
        asserts.okAssertion(createUserResponse);

/* Check if User created */
        var userByNameResponse = userCont.getUserByName(targetUser.getUsername());
        asserts.okAssertion(userByNameResponse);

/* Update User */
        var updateUserResponse = userCont
                .updateUser(targetUserUpdated, targetUser.getUsername());
        asserts.okAssertion(updateUserResponse);
/* Check if User created */
        var searchUpdatedUserResponse = userCont
                .getUserByName(targetUserUpdated.getUsername());

        User actualUpdatedUser = searchUpdatedUserResponse.as(User.class);

        asserts.assertCreateUserBody(targetUserUpdated, actualUpdatedUser);
        asserts.okAssertion(searchUpdatedUserResponse);

/* Delete User after test passed */
        Response userDeleted = userCont
                .deleteUserByUsername(targetUserUpdated.getUsername());
        asserts.okAssertion(userDeleted);

    }
}

