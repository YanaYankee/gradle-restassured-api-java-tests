package com.swagger.api.data;

import com.github.javafaker.Faker;
import com.swagger.petstore.models.User;

public class UserDataGen {
    Faker faker = new Faker();
    public User generateDataToCreateUser() {

    Long targetId = faker.number().randomNumber();
    String targetUserName = faker.name().username();
    String targetFirstName = faker.name().firstName();
    String targetLastName = faker.name().lastName();
    String targetEmail = faker.internet().emailAddress();
    String targetPassword = faker.internet().password(true);
    String targetPhone = faker.phoneNumber().phoneNumber();
    int targetUserStatus = 0;


    User targetUser = new User()
            .id(targetId)
            .username(targetUserName)
            .firstName(targetFirstName)
            .lastName(targetLastName)
            .email(targetEmail)
            .password(targetPassword)
            .phone(targetPhone)
            .userStatus(targetUserStatus);
    return targetUser;
    }



}
