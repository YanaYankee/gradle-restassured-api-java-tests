package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.asserts.Asserts;
import com.swagger.api.common.ResponseExpectMessages;
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

@ExtendWith(ReportPortalExtension.class)
public class UserSearchTests {

    static {
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
                .build();
    }

    UserController userCont = new UserController();
    UserDataGen userData = new UserDataGen();
    Asserts asserts = new Asserts();

    @Test
    @DisplayName("Search for existing User")
    void searchForExistingUser() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
        /* Search existing User to check server response */
        userCont
                .searchUserByUsernameAssertion(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        var userByNameResponse = userCont.getUserByName(targetUser.getUsername());
        User actualUser = userByNameResponse.as(User.class);

        asserts.assertCreateUserBody(targetUser, actualUser);
    }


    @Test
    @DisplayName("Delete not existing User, 404 Not found check")
    void searchForNotExistingUser() {

        userCont
                .searchUserByUsernameAssertion(";elfjafj;ojdf;ojeosfjosfosjdfsjfojdi")
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.NOT_FOUND);

    }

}

