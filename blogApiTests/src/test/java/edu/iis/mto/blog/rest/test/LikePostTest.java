package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LikePostTest extends FunctionalTests {

    private static final String LIKE_POST_API = "/blog/user/{userId}/like/{postId}";
    private static final long POST_AUTHOR_ID = FuncTestsUtils.CONFIRMED_USER_ID;
    private static final long POST_ID = 1L;

    @BeforeAll
    static void createSaveBlogPost() {
        JSONObject jsonObj = new JSONObject().put("entry", "entry");
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .post("/blog/user/{userId}/post", POST_AUTHOR_ID);
    }


    @Test
    void shouldReturnBadRequestStatusWhenAuthorAttemptsToLikeHisOwnPost() {
        given().header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(LIKE_POST_API, POST_AUTHOR_ID, POST_ID);
    }
}
