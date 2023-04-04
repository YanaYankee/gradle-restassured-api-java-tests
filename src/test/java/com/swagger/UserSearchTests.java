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

public class UserSearchTests {

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
    @DisplayName("Search for existing User")
    void searchForExistingUser() {
 /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        var createUserResponse = userCont
                .createNewUserAuth(targetUser);
        asserts.okAssertion(createUserResponse);
/* Search existing User to check server response */
        Response userSearchResult = userCont
                .searchUserByUsername(targetUser.getUsername());
        asserts.okAssertion(userSearchResult);

        var userByNameResponse = userCont.getUserByName(targetUser.getUsername());
        User actualUser = userByNameResponse.as(User.class);

        asserts.assertCreateUserBody(targetUser, actualUser);
    }
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete not existing User, 404 Not found check")
    void searchForNotExistingUser() {

        Response userSearchResult = userCont
                .searchUserByUsername(";elfjafj;ojdf;ojeosfjosfosjdfsjfojdi");
        asserts.notFoundAssertion(userSearchResult);

    }

}

