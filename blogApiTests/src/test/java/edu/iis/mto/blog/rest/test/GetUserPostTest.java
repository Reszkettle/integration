package edu.iis.mto.blog.rest.test;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetUserPostTest extends FunctionalTests {

    private static final String GET_USER_POSTS_API = "/blog/user/{userId}/post";

    @Test
    void shouldReturnBadRequestStatusCodeWhenUserIsRemoved() {
       given().header(FuncTestsUtils.REQUEST_HEADER)
              .when()
              .get(GET_USER_POSTS_API, FuncTestsUtils.REMOVED_USER_ID)
              .then()
              .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
