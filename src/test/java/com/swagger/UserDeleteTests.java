package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.asserts.Asserts;
import com.swagger.api.common.ResponseAssertion;
import com.swagger.api.common.ResponseExpectMessages;
import com.swagger.api.controller.BaseController;
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
public class UserDeleteTests extends BaseController {

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
    @DisplayName("Delete existing User")
    void deleteExistingUser() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.NOT_FOUND);

        /* Delete existing User to check server response */
        ResponseAssertion userDeleted = userCont
                .deleteUserByUsername(targetUser.getUsername());
        userDeleted.statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Check if deleted with search for User */
        userCont
                .searchUserByUsernameAssertion(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.NOT_FOUND);
    }


    @Test
    @DisplayName("Delete existing User with mistyped (first letter in upper case) userName")
    void deleteExistingUserWithMistypedUserName() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
        /* Delete existing User with mistyped userName to check server response */
        assert targetUser.getUsername() != null;
        ResponseAssertion userDeleted = userCont
                .deleteUserByUsername(makeFirstLetterUpperCase(targetUser.getUsername()));
    }


    @Test
    @DisplayName("Delete not existing User, 404 Not found check")
    void deleteNotExistingUser() {
        /* Delete not existing user to check 404 response code*/
        ResponseAssertion userDeleted = userCont
                .deleteUserByUsername("ornsierfisnuveifhiseufnaivufeuivgs");
        userDeleted.statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.NOT_FOUND);
    }


    @Test
    @DisplayName("Delete User with empty username to check 405 Method not allowed")
    void deleteUserWithEmptyUsername() {
        /* Delete User fetching empty userName to check 405 response code*/
        ResponseAssertion userDeleted = userCont.deleteUserByUsername("");
        userDeleted
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.NOT_ALLOWED);
    }


    @Test
    @DisplayName("Delete User with invalid username")
    void deleteUserWithDigitalUserName() {
        /* Delete not existing user to check 404 response code*/
        ResponseAssertion userDeleted = userCont
                .deleteUserByUsername("12123123");
        userDeleted.statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.NOT_FOUND);
    }
}

