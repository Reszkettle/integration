package edu.iis.mto.blog.rest.test;

import io.restassured.http.Header;

public class FuncTestsUtils {

    public static final Header HEADER = new Header("Content-Type", "application/json;charset=UTF-8");
    public static final long CONFIRMED_USER_ID = 1L;
    public static final long REMOVED_USER_ID = 2L;
    public static final long NEW_USER_ID = 3L;

}
