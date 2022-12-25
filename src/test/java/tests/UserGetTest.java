package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
//import org.junit.jupiter.api.Assertions;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
//import sun.jvm.hotspot.HSDB;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {

    //неавторизованный запрос на данные - там мы получили только username
    @Test
    public void testGetUserDateNotAuth() {

        String link = "https://playground.learnqa.ru/api/user/2";
        Response responseUSerDate = RestAssured
                .get(link)
                .andReturn();

        //System.out.println(response.asString());
        Assertions.assertJsonHasField(responseUSerDate, "username");
        Assertions.assertJsonHasNotField(responseUSerDate, "firstName");
        Assertions.assertJsonHasNotField(responseUSerDate, "lastName");
        Assertions.assertJsonHasNotField(responseUSerDate, "email");
    }

    //авторизованный запрос - мы были авторизованы пользователем с ID 2 и
    // делали запрос для получения данных того же пользователя, в этом случае мы получали все поля
    @Test
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        responseGetAuth.prettyPrint();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");


        Response responseUserDate = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        responseUserDate.prettyPrint();

        String[] expectedFields = {"username", "lastName", "firstName", "email"};

        Assertions.assertJsonHasFields(responseUserDate, expectedFields);

/*
        Assertions.assertJsonHasField(responseUserDate, "username");
        Assertions.assertJsonHasField(responseUserDate, "firstName");
        Assertions.assertJsonHasField(responseUserDate, "lastName");
        Assertions.assertJsonHasField(responseUserDate, "email");
*/

    }

    //авторизация одного пользователя, но с другим ID.
    //запрос должен получить только username, не должны приходить остальные данные чужого пользователя.
    @Test
    public void testGetUserDetailsAuthWithOtherId() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");


        //Создание нового пользователя для получения другого user_id
        Map<String, String> userDate = DataGenerator.getRegistrationData();

        String link = "https://playground.learnqa.ru/api/user/";

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .jsonPath();

        String other_id = responseCreateAuth.getString("id");
        //запрос одного ползователя с id от другого пользователя
        Response responseUserDate = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/" + other_id)
                .andReturn();

        String expectedFields = "username";

        Assertions.assertJsonHasField(responseUserDate, expectedFields);

    }

}

