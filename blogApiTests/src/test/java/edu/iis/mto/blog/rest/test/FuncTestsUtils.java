package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class FuncTestsUtils {

    public static final Header REQUEST_HEADER = new Header("Content-Type", "application/json;charset=UTF-8");
    public static final long CONFIRMED_USER_ID = 1L;
    public static final long REMOVED_USER_ID = 2L;
    public static final long NEW_USER_ID = 3L;

    public static int getCountOfPostLikesForPostById(long postId) {
        final String POST_API = "/blog/post/{id}";
        RestAssured.defaultParser = Parser.JSON;
        return given().accept(ContentType.JSON)
                .header(REQUEST_HEADER)
                .when().get(POST_API, postId)
                .then().contentType(ContentType.JSON).extract().response().jsonPath().getInt("likesCount");
    }

    public static void likePost(long postId, long userId) {
        String api = "/blog/user/{userId}/like/{postId}";
        given().header(FuncTestsUtils.REQUEST_HEADER)
                .post(api, userId, postId);
    }

    public static long createSamplePost(long userId) {
        String api = "/blog/user/{userId}/post";
        JSONObject jsonObj = new JSONObject().put("entry", "entry");
        String jsonString = jsonObj.toString();
        return given().accept(ContentType.JSON)
                .header(REQUEST_HEADER)
                .body(jsonString)
                .post(api, userId)
                .then()
                .extract()
                .jsonPath()
                .getLong("id");
    }

}
