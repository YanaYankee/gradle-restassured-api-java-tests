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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.requestSpecification;

public class UsersWithListCreationTests {


  static {requestSpecification = new RequestSpecBuilder()
          .log(LogDetail.ALL)
          //.addFilter(new AllureRestAssured())
          .addHeader("X-Traicing-Id", UUID.randomUUID().toString())
          .build();
  }

    ResponseAsserts asserts = new ResponseAsserts();
    UserDataGen userData = new UserDataGen();
    @ExtendWith(ReportPortalExtension.class)
    @Test
    @DisplayName("Creation of Users with input array")
    void creationOfUsersWithList() {
        UserController userCont = new UserController();

        /* Create Users with API call */
        int objNumber = 5;
        int i = 0;
        int y = 0;
        List<User> users = new ArrayList<>();
        List<String> userNames = new ArrayList<>();

        while(i<objNumber)
            {
                User user = userData.generateDataToCreateUser();
                userNames.add(user.getUsername());
                users.add(user);
                i++;
            }

        var createUserResponse = userCont
                .createUsersWithListAuth(users);
        asserts.okAssertion(createUserResponse);



        while(y<userNames.size())
        {
            /* Check if User created */
            var userByNameResponse = userCont.searchUserByUsername(userNames.get(y));
            User actualUser = userByNameResponse.as(User.class);

            asserts.assertCreateUserBody(users.get(y), actualUser);
            asserts.okAssertion(userByNameResponse);
            /* Delete User after test passed */
            Response userDeleted = userCont
                    .deleteUserByUsername(userNames.get(y));
            asserts.okAssertion(userDeleted);
            y++;
        }




    }
}

