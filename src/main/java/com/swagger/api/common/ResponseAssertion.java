package com.swagger.api.common;

import com.epam.reportportal.annotations.Step;
import com.swagger.api.controller.UserServiceErrors;
import com.swagger.api.data.ResponseCodes;
import com.swagger.petstore.models.User;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

import static org.assertj.core.api.Assertions.*;

public class ResponseAssertion {

    private final Response targetResponse;
    public ResponseAssertion(Response targetResponse){
        this.targetResponse = targetResponse;
    }

    public ResponseAssertion statusCodeIsEqualTo(ResponseExpectMessages.StatusCode expectedStatusCode){
        var statusCodeAssertionMessage = new ResponseExpectMessages(targetResponse)
                .expectedStatusesCode(expectedStatusCode);
        assertThat(targetResponse.statusCode())
                .withFailMessage(statusCodeAssertionMessage)
                .isEqualTo(expectedStatusCode.code);
        return this;
    }

    public ResponseAssertion errorMessageIsEqual(UserServiceErrors targetError){
        var espectedErrorResponse = ErrorResponse.builder()
                .code(targetError.getCode())
                .message(targetError.getMessage())
                .build();

        assertThat(bindAs(ErrorResponse.class))
                .isEqualToIgnoringGivenFields("stackTrace")
                .isEqualTo(espectedErrorResponse);

        return this;
    }


    public <T> T bindAs(Class<T> expectedClass){
        T convertedObject;
        try{
            convertedObject = targetResponse.as(expectedClass);
        } catch (Exception ex){
            var assertionMessage = new ResponseExpectMessages(targetResponse)
                    .expectedResponseBodyClass(expectedClass);
            throw new AssertionError(assertionMessage);
        }
        return convertedObject;
    }

    public ObjectAssert<Object> objectTOAssert(Class expectedClass){
        return assertThat(bindAs(expectedClass));
    }

    public <T> T[] bindAsListOf(Class<T[]> expectedClass){
        return bindAs(expectedClass);
    }

    public void setTargetResponse(Object expectedObject){
        var objectUnderTest = bindAs(expectedObject.getClass());

        assertThat(objectUnderTest).isEqualTo(expectedObject);
    }


    private void assertions(ResponseCodes code, Response response) {
        org.junit.jupiter.api.Assertions.assertEquals(code.getStatus(), response.statusCode());
        org.junit.jupiter.api.Assertions.assertEquals(code.getStatusLine(), response.statusLine());
    }

    @Step("Assert Actual response body is equal to expected")
    public void assertCreateUserBody(User expectUser, User actUser) {
        org.junit.jupiter.api.Assertions.assertEquals(expectUser, actUser);
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
