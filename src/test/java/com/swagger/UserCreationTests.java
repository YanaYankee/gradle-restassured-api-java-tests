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
public class UserCreationTests {


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
    void creationOfANewUserViaApiDecorator() {


        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Check if User created */
        var userByNameResponse = userCont
                .getUserByNameAssertion(targetUser.getUsername());
        userByNameResponse
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Delete User after test passed */
        userCont
                .deleteUserByUsername(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

    }
}

