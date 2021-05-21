package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import io.restassured.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

class CreateUserTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    @Test
    void createUserWithProperDataReturnsCreatedStatus() {
        JSONObject jsonObj = new JSONObject().put("email", "tracy1@domain.com");
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_CREATED)
               .when()
               .post(USER_API);
    }

    @Test
    void shouldReturn409StatusCodeOnCreateWhenUserAlreadyExists() {
        JSONObject jsonObj = new JSONObject().put("email", "tracy1@domain.com");
        String jsonString = jsonObj.toString();
        Header header = new Header("Content-Type", "application/json;charset=UTF-8");

        given().accept(ContentType.JSON)
                .header(header)
                .body(jsonString)
                .post(USER_API);

        given().accept(ContentType.JSON)
                .header(header)
                .body(jsonString)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT)
                .when()
                .post(USER_API);
    }
}
