package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class CreateUserTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    @Test
    void createUserWithProperDataReturnsCreatedStatus() {
        JSONObject jsonObj = new JSONObject().put("email", "tracy1@domain.com");
        given().accept(ContentType.JSON)
               .header(FuncTestsUtils.header)
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

        given().accept(ContentType.JSON)
                .header(FuncTestsUtils.header)
                .body(jsonString)
                .post(USER_API);

        given().accept(ContentType.JSON)
                .header(FuncTestsUtils.header)
                .body(jsonString)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT)
                .when()
                .post(USER_API);
    }
}
