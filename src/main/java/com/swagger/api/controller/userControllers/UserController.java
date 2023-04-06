package com.swagger.api.controller.userControllers;

import com.epam.reportportal.annotations.Step;
import com.swagger.api.controller.BaseController;
import com.swagger.api.data.AdminData;
import com.swagger.petstore.models.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class UserController extends BaseController {

    @Step("Create user")
    public Response createNewUserAuth(User userDto) {
        return userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(userDto)
                .post();
    }

    @Step("Create Users from list")
    public Response createUsersWithArrayAuth(List<User> users) {
        return userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(users)
                .post("/createWithArray");
    }

    @Step("Create user")
    public Response createUsersWithListAuth(List<User> users) {
        return userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(users)
                .post("/createWithList");
    }

    @Step("Update user by userName")
    public Response updateUserAuth(User userDto, String targetUserName) {
        return userApi()
                .auth()
                .preemptive()
                .basic(AdminData.ADMIN_USER_NAME.getValue(), AdminData.ADMIN_PASSWORD.getValue())
                .body(userDto)
                .put("/{username}", targetUserName);
    }

    @Step("Get user by userName")
    public Response getUserByName(String targetUserName) {
        return userApi().get("/{username}", targetUserName);
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
    public Response deleteUserByUsername(String targetUserName) {
        Response response = userApi()
                .delete("/{username}", targetUserName);
        System.out.println("RESPONSE deleteUserByUsername: " + response.getStatusLine());
        return response;
    }

    @Step("Search (get) User by userName")
    public Response searchUserByUsername(String targetUserName) {
        Response response = userApi()
                .get("/{username}", targetUserName);
        System.out.println("RESPONSE searchUserByUsername: " + response.getStatusLine());
        return response;
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