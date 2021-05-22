package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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

    @Test
    void shouldNotReturnAnyPostsWhenUserDidntCreateAny() {
        given().header(FuncTestsUtils.REQUEST_HEADER)
               .accept(ContentType.JSON)
               .when()
               .get(GET_USER_POSTS_API, FuncTestsUtils.CONFIRMED_USER_ID)
               .then()
               .body("size()", equalTo(0));
    }
}
