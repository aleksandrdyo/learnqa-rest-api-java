package tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lib.Assertions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        String link = "https://playground.learnqa.ru/api/user/login";
        //String link1 = "https://playground.learnqa.ru/api/user/auth";

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(link)
                .andReturn();

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");

    }

    @Test
    public void testAuthUser(){

/*
        Map<String, String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();
        int userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");

        assertEquals(200, responseGetAuth.statusCode(), "Unexpected status code");
        assertTrue(cookies.containsKey("auth_sid"), "Response doesn't have auth_sid cookie");
        assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Response doesn't have x-csrf-token header");
        assertTrue(responseGetAuth.jsonPath().getInt("user_id") > 0, "Userid should be greater then 0");
*/

        Response responseChekAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();

        Assertions.assertJsonByName(responseChekAuth, "user_id", this.userIdOnAuth);

/*
        int userIdOnCheck = responseChekAuth.getInt("user_id");
        assertTrue(userIdOnCheck>0, "Unexpected userid " + userIdOnCheck);

        assertEquals(
                userIdOnAuth,
                userIdOnCheck,
                "user id from auth on request is not equal to user id from check request"
        );
*/

    }

    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition){
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if (condition.equals("cookie")){
            spec.cookie("auth_sid", this.cookie);
        }else if (condition.equals("headers")){
            spec.header("x-csrf-token", this.header);
        }else {
            throw new IllegalArgumentException("Condition value is known: " + condition);
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
        //assertEquals(0, responseForCheck.getInt("user_id"), "user_id should be 0 for unauth request");

    }
}
