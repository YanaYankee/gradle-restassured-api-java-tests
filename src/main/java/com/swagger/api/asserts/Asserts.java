package com.swagger.api.asserts;

import com.epam.reportportal.annotations.Step;
import com.swagger.api.data.ResponseCodes;
import com.swagger.petstore.models.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class Asserts {
    private void assertions(ResponseCodes code, Response response) {
        Assertions.assertEquals(code.getStatus(), response.statusCode());
        Assertions.assertEquals(code.getStatusLine(), response.statusLine());
    }

    @Step("Assert Actual response body is equal to expected")
    public void assertCreateUserBody(User expectUser, User actUser) {
        Assertions.assertEquals(expectUser, actUser);
    }

    @Step("Assert 200 OK response line")
    public void okAssertion(Response response) {
        this.assertions(ResponseCodes.OK, response);
    }

    @Step("Assert 400 Invalid response line")
    public void invalidAssertion(Response response) {
        this.assertions(ResponseCodes.INVALID, response);
    }

    @Step("Assert 404 Not Found response line")
    public void notFoundAssertion(Response response) {
        this.assertions(ResponseCodes.NOT_FOUND, response);
    }

    @Step("Assert 405 Method Not Allowed response line")
    public void notAllowedAssertion(Response response) {
        this.assertions(ResponseCodes.NOT_ALLOWED, response);
    }

    @Step("Assert 406 Not Acceptable response line")
    public void notAcceptableAssertion(Response response) {
        this.assertions(ResponseCodes.NOT_ACCEPTABLE, response);
    }

}
