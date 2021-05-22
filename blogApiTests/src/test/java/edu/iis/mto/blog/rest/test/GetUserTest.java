package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static edu.iis.mto.blog.rest.test.FuncTestsUtils.REQUEST_HEADER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetUserTest extends FunctionalTests {

    private static final String FIND_USER_API = "/blog/user/find";
    private static final String GET_USER_API = "/blog/user/{id}";

    @Test
    void shouldFindThreeUsersByEmailSubstring() {
        String emailSubstring = "gmail";

        given().param("searchString", emailSubstring)
                .accept(ContentType.JSON)
                .header(REQUEST_HEADER)
                .when().get(FIND_USER_API)
                .then().body("size()", equalTo(3));
    }

    @Test
    void shouldNotFindAnyUsersByGivenSearchString() {
        String substring = "a-b-c-d-e-f-g";

        given().param("searchString", substring)
                .accept(ContentType.JSON)
                .header(REQUEST_HEADER)
                .when().get(FIND_USER_API)
                .then().body("size()", equalTo(0));
    }

    @Test
    void shouldReturnCorrectUserData() {

        String expectedFirstName = "Jakub";
        String expectedLastName = "Reszka";
        String expectedEmail = "jakub.reszka@gmail.com";

        given()
                .accept(ContentType.JSON)
                .header(REQUEST_HEADER)
                .when().get(GET_USER_API, FuncTestsUtils.CONFIRMED_USER_ID)
                .then()
                .body("firstName", equalTo(expectedFirstName))
                .body("lastName", equalTo(expectedLastName))
                .body("email", equalTo(expectedEmail));
    }
}
