package com.swagger.api.data;

import com.github.javafaker.Faker;
import com.swagger.petstore.models.User;

import java.util.ArrayList;
import java.util.List;

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

    public List<User> generateUsersArrayObj(int objNumber) {
        int i = 0;
        List<User> users = new ArrayList<>();

        while (i < objNumber) {
            User user = this.generateDataToCreateUser();
            users.add(user);
            i++;
        }
        return users;
    }

    public List<String> generateUserNamesArrayObj(List<User> users) {

        int i = 0;
        List<String> userNames = new ArrayList<>();

        while (i < users.size()) {
            userNames.add(users.get(i).getUsername());
            i++;
        }
        return userNames;
    }


}
