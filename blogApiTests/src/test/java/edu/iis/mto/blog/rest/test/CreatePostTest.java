package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreatePostTest extends FunctionalTests {

    private static final String CREATE_POST_API = "/blog/user/{userId}/post";


    @Test
    void shouldReturnCreatedStatusWhenUserIsConfirmed() {
        JSONObject jsonObj = new JSONObject().put("entry", "default entry");
        String jsonString = jsonObj.toString();

        given().accept(ContentType.JSON)
                .header(FuncTestsUtils.HEADER)
                .body(jsonString)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(CREATE_POST_API, FuncTestsUtils.CONFIRMED_USER_ID);
    }

}
