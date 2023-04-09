package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.asserts.Asserts;
import com.swagger.api.common.ResponseExpectMessages;
import com.swagger.api.controller.userControllers.UserController;
import com.swagger.api.data.UserDataGen;
import com.swagger.petstore.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static io.restassured.RestAssured.requestSpecification;

@ExtendWith(ReportPortalExtension.class)
public class UserUpdateTests {


    static {
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                //.addFilter(new AllureRestAssured())
                .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
                .build();
    }

    Asserts asserts = new Asserts();
    UserDataGen userData = new UserDataGen();
    UserController userCont = new UserController();

    @Test
    @DisplayName("Creation of a new User with required data present")
    void creationOfANewUserViaApi() {

        /* Generate data for user create and user update */
        User targetUser = userData.generateDataToCreateUser();
        User targetUserUpdated = userData.generateDataToCreateUser();
        /* Create new User with API call */
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Check if User created */
        var userByNameResponse = userCont.getUserByName(targetUser.getUsername());
        asserts.okAssertion(userByNameResponse);

        /* Update User */
        userCont
                .updateUserAuth(targetUserUpdated, targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
        /* Check if User created */
        var searchUpdatedUserResponse = userCont
                .getUserByName(targetUserUpdated.getUsername());

        User actualUpdatedUser = searchUpdatedUserResponse.as(User.class);

        asserts.assertCreateUserBody(targetUserUpdated, actualUpdatedUser);
        asserts.okAssertion(searchUpdatedUserResponse);

        /* Delete User after test passed */
        userCont
                .deleteUserByUsername(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

    }
}

