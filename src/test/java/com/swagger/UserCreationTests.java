package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.asserts.Asserts;
import com.swagger.api.controller.userControllers.UserController;
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

public class UserCreationTests {


  static {requestSpecification = new RequestSpecBuilder()
          .log(LogDetail.ALL)
          //.addFilter(new AllureRestAssured())
          .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
          .build();
  }

    Asserts asserts = new Asserts();
    UserDataGen userData = new UserDataGen();
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Creation of a new User with required data present")
    void creationOfANewUserViaApi() {
        UserController userCont = new UserController();
/* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        var createUserResponse = userCont
                .createNewUserAuth(targetUser);
        asserts.okAssertion(createUserResponse);

/* Check if User created */
        var userByNameResponse = userCont.getUserByName(targetUser.getUsername());
        User actualUser = userByNameResponse.as(User.class);

        asserts.assertCreateUserBody(targetUser, actualUser);
        asserts.okAssertion(userByNameResponse);

/* Delete User after test passed */
        Response userDeleted = userCont
                .deleteUserByUsername(targetUser.getUsername());
        asserts.okAssertion(userDeleted);

    }
}

