package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.swagger.api.asserts.Asserts;
import com.swagger.api.controller.BaseController;
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

    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete existing User")
    void deleteExistingUser() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        var createUserResponse = userCont
                .createNewUserAuth(targetUser);
        asserts.okAssertion(createUserResponse);
        /* Delete existing User to check server response */
        Response userDeleted = userCont
                .deleteUserByUsername(targetUser.getUsername());
        asserts.okAssertion(userDeleted);
        /* Check if deleted with search for User */
        Response deletedUserSearch = userCont
                .searchUserByUsername(targetUser.getUsername());
        asserts.notFoundAssertion(deletedUserSearch);
    }

    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete existing User with mistyped (first letter in upper case) userName")
    void deleteExistingUserWithMistypedUserName() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        var createUserResponse = userCont
                .createNewUserAuth(targetUser);
        asserts.okAssertion(createUserResponse);
        /* Delete existing User with mistyped userName to check server response */
        assert targetUser.getUsername() != null;
        Response userDeleted = userCont
                .deleteUserByUsername(makeFirstLetterUpperCase(targetUser.getUsername()));
        asserts.notFoundAssertion(userDeleted);
    }

    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete not existing User, 404 Not found check")
    void deleteNotExistingUser() {
        /* Delete not existing user to check 404 response code*/
        Response userDeleted = userCont
                .deleteUserByUsername("ornsierfisnuveifhiseufnaivufeuivgs");
        asserts.notFoundAssertion(userDeleted);
    }

    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete User with empty username to check 405 Method not allowed")
    void deleteUserWithEmptyUsername() {
        /* Delete User fetching empty userName to check 405 response code*/
        Response userDeleted = userCont.deleteUserByUsername("");
        System.out.println("RESPONSE userDeleted: " + userDeleted.asString());
        asserts.notAllowedAssertion(userDeleted);
    }

    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Delete User with invalid username")
    void deleteUserWithDigitalUserName() {
        /* Delete not existing user to check 404 response code*/
        Response userDeleted = userCont.deleteUserByUsername("12123123");
        asserts.notFoundAssertion(userDeleted);
    }
}

