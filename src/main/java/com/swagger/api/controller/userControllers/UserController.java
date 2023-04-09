package com.swagger.api.controller.userControllers;

import com.epam.reportportal.annotations.Step;
import com.swagger.api.common.ResponseAssertion;
import com.swagger.api.controller.BaseController;
import com.swagger.api.data.AdminData;
import com.swagger.petstore.models.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class UserController extends BaseController {

    @Step("Create user")
    public ResponseAssertion createNewUserAuth(User userDto) {
        return new ResponseAssertion(userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(userDto)
                .post()
        );
    }

    @Step("Create Users from list")
    public ResponseAssertion createUsersWithArrayAuth(List<User> users) {
        return new ResponseAssertion(userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(users)
                .post("/createWithArray")
        );
    }

    @Step("Create user")
    public ResponseAssertion createUsersWithListAuth(List<User> users) {
        return new ResponseAssertion(userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(users)
                .post("/createWithList")
        );
    }

    @Step("Update user by userName")
    public ResponseAssertion updateUserAuth(User userDto, String targetUserName) {
        return new ResponseAssertion(userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(userDto)
                .put("/{username}", targetUserName)
        );
    }

    @Step("Get user by userName")
    public Response getUserByName(String targetUserName) {
        return userApi().get("/{username}", targetUserName);
    }

    @Step("Get user by userName")
    public ResponseAssertion getUserByNameAssertion(String targetUserName) {
        return new ResponseAssertion(userApi().get("/{username}", targetUserName));
    }

    private RequestSpecification userApi() {
        return petStoreApiClient("/user");
    }

    @Step("Log in existing User with valid creds")
    public Response logInUserWithValidCreds(String targetUserName, String targetPass) {
        Response response = userApi().auth()
                .preemptive()
                .basic(targetUserName, targetPass)
                .get("/login");
        System.out.println("RESPONSE logInUserWithValidCreds: " + response.getStatusLine());
        return response;
    }

    @Step("Log in existing User with not valid Pass")
    public Response logInUserWithNotValidPass(String targetUserName, String targetPass) {
        Response response = userApi().auth()
                .preemptive()
                .basic(targetUserName, targetPass)
                .get("/login");
        System.out.println("RESPONSE logInUserWithNotValidPass: " + response.getStatusLine());
        return response;
    }

    @Step("Log in existing User with mistyped userName ")
    public Response logInUserWithMistypedUserName(String targetUserName, String targetPass) {
        Response response = userApi().auth()
                .preemptive()
                .basic(targetUserName, targetPass)
                .get("/login");
        System.out.println("RESPONSE logInUserWithMistypedUserName: " + response.getStatusLine());
        return response;
    }

    @Step("Delete User by userName")
    public ResponseAssertion deleteUserByUsername(String targetUserName) {
        return new ResponseAssertion(userApi()
                .delete("/{username}", targetUserName));
    }

    @Step("Search (get) User by userName")
    public Response searchUserByUsername(String targetUserName) {
        Response response = userApi()
                .get("/{username}", targetUserName);
        System.out.println("RESPONSE searchUserByUsername: " + response.getStatusLine());
        return response;
    }

    @Step("Search (get) User by userName")
    public ResponseAssertion searchUserByUsernameAssertion(String targetUserName) {
        return new ResponseAssertion(userApi()
                .get("/{username}", targetUserName)
        );
    }

    @Step("Get User Session id")
    public String getUserSessionId(String targetUserName, String password) {
        var loginResponse = this
                .logInUserWithValidCreds(targetUserName, password);
        String sessionIdMessage = getJsonPath(loginResponse, "message");
        System.out.println("sessionIdMessage: " + sessionIdMessage);
        String sessionId =
                sessionIdMessage.substring(sessionIdMessage.indexOf(":") + 1);
        System.out.println("sessionId: " + sessionId);

        return sessionId;
    }

    @Step("Log out existing User")
    public Response logOutWithValidSessionId(String sessionId) {
        Response response = userApi()
                .get("/logout");
        System.out.println("RESPONSE logOutWithValidSessionId: " + response.getStatusLine());
        return response;

    }
}