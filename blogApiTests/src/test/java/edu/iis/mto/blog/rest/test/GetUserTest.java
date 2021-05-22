package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static edu.iis.mto.blog.rest.test.FuncTestsUtils.REQUEST_HEADER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetUserTest extends FunctionalTests {

    private static final String FIND_USER_API = "/blog/user/find";

    @Test
    void shouldFindFourUsersByEmailSubstring() {
        String emailSubstring = "gmail";

        given().param("searchString", emailSubstring)
                .accept(ContentType.JSON)
                .header(REQUEST_HEADER)
                .when().get(FIND_USER_API)
                .then().body("size()", equalTo(4));
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
}
