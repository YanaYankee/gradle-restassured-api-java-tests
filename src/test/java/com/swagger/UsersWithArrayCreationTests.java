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

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.requestSpecification;

@ExtendWith(ReportPortalExtension.class)
public class UsersWithArrayCreationTests {


    static {
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                //.addFilter(new AllureRestAssured())
                .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
                .build();
    }

    Asserts asserts = new Asserts();
    UserDataGen userData = new UserDataGen();

    @Test
    @DisplayName("Creation of Users with input array")
    void creationOfUsersWithArray() {
        UserController userCont = new UserController();

        /* Create Users with API call */
        List<User> users = userData.generateUsersArrayObj(5);
        List<String> userNames = userData.generateUserNamesArrayObj(users);

        userCont
                .createUsersWithListAuth(users)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);


        for (int y = 0; y < userNames.size(); y++) {
            /* Check if User created */
            var userByNameResponse = userCont.searchUserByUsername(userNames.get(y));
            User actualUser = userByNameResponse.as(User.class);

            asserts.assertCreateUserBody(users.get(y), actualUser);
            asserts.okAssertion(userByNameResponse);
            /* Delete User after test passed */
            userCont
                    .deleteUserByUsername(userNames.get(y))
                    .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
        }
    }

    @Test
    @DisplayName("Creation of Users with input array consisting of 1 User object")
    void creationOfUsersWithArrayOfOneUser() {
        UserController userCont = new UserController();

        /* Create Users with API call */
        List<User> users = userData.generateUsersArrayObj(1);
        List<String> userNames = userData.generateUserNamesArrayObj(users);

        userCont
                .createUsersWithListAuth(users)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);


        for (int y = 0; y < userNames.size(); y++) {
            /* Check if User created */
            var userByNameResponse = userCont.searchUserByUsername(userNames.get(y));
            User actualUser = userByNameResponse.as(User.class);

            asserts.assertCreateUserBody(users.get(y), actualUser);
            asserts.okAssertion(userByNameResponse);
            /* Delete User after test passed */
            userCont
                    .deleteUserByUsername(userNames.get(y))
                    .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
        }
    }

    @Test
    @DisplayName("Creation of Users with input array consisting of 200 Users object")
    void creationOfUsersWithArrayOfTwoHundredUsers() {
        UserController userCont = new UserController();

        /* Create Users with API call */
        List<User> users = userData.generateUsersArrayObj(200);
        List<String> userNames = userData.generateUserNamesArrayObj(users);

        userCont
                .createUsersWithListAuth(users)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);


        for (int y = 0; y < userNames.size(); y++) {
            /* Check if User created */
            System.out.println("CHECK USER " + y);
            var userByNameResponse = userCont.searchUserByUsername(userNames.get(y));
            User actualUser = userByNameResponse.as(User.class);

            asserts.assertCreateUserBody(users.get(y), actualUser);
            asserts.okAssertion(userByNameResponse);;
            /* Delete User after test passed */
            userCont
                    .deleteUserByUsername(userNames.get(y))
                    .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
        }
    }
}

