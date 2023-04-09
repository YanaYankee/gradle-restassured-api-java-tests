package com.swagger;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.github.javafaker.Faker;
import com.swagger.api.asserts.Asserts;
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
public class UserLogInTests extends BaseController {

    static {
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
                .build();
    }

    Faker faker = new Faker();
    UserController userCont = new UserController();
    UserDataGen userData = new UserDataGen();
    Asserts asserts = new Asserts();


    @Test
    @DisplayName("Log in User with valid credentials")
    void loginUserWithValidCreds() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Log in this new User to system */
        var loginResponse = userCont
                .logInUserWithValidCreds(targetUser.getUsername(), targetUser.getPassword());
        asserts.okAssertion(loginResponse);

        /* Delete User after test passed */
        userCont
                .deleteUserByUsername(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
    }

    /* The following tests loginUserWithNotExistingCreds is only for
    the sake of test coverage coz indeed as there are no limitations as to the creds,
    any userName and Pass get 200 response  */

    @Test
    @DisplayName("Log in User with valid credentials")
    void loginUserWithNotExistingCreds() {

        /* Log in this new User to system */
        var loginResponse = userCont
                .logInUserWithValidCreds("sefresvesrfvesrfsverfserfvesrfv", "ssda");
        asserts.okAssertion(loginResponse);

    }

    @Test
    @DisplayName("Log in User with valid userName and not valid Password")
    void loginUserWithNotValidPassCreds() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();
        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Log in this new User to system with not valid Pass*/
        var loginResponse = userCont
                .logInUserWithNotValidPass(targetUser.getUsername(), "dofjvldjvljdlvsdvksdnds");
        asserts.notAcceptableAssertion(loginResponse);

        /* Delete User after test passed */
        userCont
                .deleteUserByUsername(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
    }

    @Test
    @DisplayName("Log in User with valid mistyped userName and valid Password")
    void loginUserWithMistypedUserName() {
        /* Create new User with API call */
        User targetUser = userData.generateDataToCreateUser();

        userCont
                .createNewUserAuth(targetUser)
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);

        /* Log in this new User to system with username mistyped (first letter upper case and valid Pass*/
        var loginResponse = userCont
                .logInUserWithNotValidPass(makeFirstLetterUpperCase(targetUser.getUsername()),
                        targetUser.getPassword());
        asserts.notAcceptableAssertion(loginResponse);

        /* Delete User after test passed */
        userCont
                .deleteUserByUsername(targetUser.getUsername())
                .statusCodeIsEqualTo(ResponseExpectMessages.StatusCode.OK);
    }

}

