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
import lib.ApiCoreRequests;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;



@Epic("Authorisation cases")//Все последующие тесты принадлежат группе единой общей части, то есть Авторизационные кейсы
@Feature("Authorisation")//Описание название фичи
public class UserAuthTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        String link = "https://playground.learnqa.ru/api/user/login";
        //String link1 = "https://playground.learnqa.ru/api/user/auth";

//        Response responseGetAuth = RestAssured
        Response responseGetAuth = apiCoreRequests
                .makePostRequest(link, authData);
                /*.given()
                .body(authData)
                .post(link)
                .andReturn();*/

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");

    }

    @Test
    //@Titl("Позитивный тест, создание нового пользователя title")
    @Description("This test successfully authorize user by email and password")//Описание что именно тест проверяет
    @DisplayName("Test positive auth user")//название теста в отчете

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

        Response responseChekAuth = apiCoreRequests
                .makeGetRequest(
                        "https://playground.learnqa.ru/api/user/auth",
                        this.header,
                        this.cookie

                );
/*                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();*/

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

    @Description("This test checks authorization status without sending auth cookie or token")
    @DisplayName("Test negative auth user")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition){
        //RequestSpecification spec = RestAssured.given();
        //spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if (condition.equals("cookie")){
            Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie(
                    "https://playground.learnqa.ru/api/user/auth",
                    this.cookie
            );
            Assertions.assertJsonByName(responseForCheck, "user_id", 0);
        } else if (condition.equals("headers")){
            Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(
                    "https://playground.learnqa.ru/api/user/auth",
                    this.header
            );
            Assertions.assertJsonByName(responseForCheck, "user_id", 0);
        } else {
            throw new IllegalArgumentException("Condition value is not known: " + condition);
        }



            /*if (condition.equals("cookie")){
            spec.cookie("auth_sid", this.cookie);
        }else if (condition.equals("headers")){
            spec.header("x-csrf-token", this.header);
        }else {
            throw new IllegalArgumentException("Condition value is known: " + condition);
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);*/
        //assertEquals(0, responseForCheck.getInt("user_id"), "user_id should be 0 for unauth request");

    }
}
