package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import lib.Assertions;
import lib.DataGenerator;
import lib.BaseTestCase;


public class UserDeleteTest extends BaseTestCase {

    @Test
    public void testUserDelete() {

        //Login test user
        Map<String, String> data = new HashMap<>();
        data.put("email", "vinkotov@example.com");
        data.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(data)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String userId = responseGetAuth.jsonPath().getString("user_id");
        String cookie = responseGetAuth.getCookie("auth_sid");
        String token = responseGetAuth.getHeader("x-csrf-token");

        //Первый - на попытку удалить пользователя по ID 2.
        // Убедиться, что система не даст вам удалить этого пользователя.
        // Его данные для авторизации:
        Response responseDelUser = RestAssured
                .given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        Assertions.assertResponseTextEquals(responseDelUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
        Assertions.assertResponseCodeEquals(responseDelUser, 400);

        //--Generate New User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userIdNew = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuthNewUser = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Второй - позитивный. Создать пользователя, авторизоваться из-под него,
        // удалить, затем попробовать получить его данные по ID и убедиться, что пользователь действительно удален.
        Response responseDelNewUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuthNewUser, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuthNewUser, "auth_sid"))
                .delete("https://playground.learnqa.ru/api/user/" + userIdNew)
                .andReturn();
        Assertions.assertResponseCodeEquals(responseDelNewUser, 200);

        Response responseCheckDelNewUser = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/user/" + userIdNew)
                .andReturn();

        Assertions.assertResponseTextEquals(responseCheckDelNewUser, "User not found");
        Assertions.assertResponseCodeEquals(responseCheckDelNewUser, 404);

        //Третий - негативный, попробовать удалить пользователя, будучи авторизованными другим пользователем.
        //--Generate New User1
        Map<String, String> userData1 = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth1 = RestAssured
                .given()
                .body(userData1)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userIdNew1 = responseCreateAuth1.getString("id");

        //Login
        Map<String, String> authData1 = new HashMap<>();
        authData1.put("email", userData1.get("email"));
        authData1.put("password", userData1.get("password"));

        Response responseGetAuthNewUser1 = RestAssured
                .given()
                .body(authData1)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //--Generate New User2
        Map<String, String> userData2 = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth2 = RestAssured
                .given()
                .body(userData2)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userIdNew2 = responseCreateAuth2.getString("id");

        //Третий - негативный, попробовать удалить пользователя, будучи авторизованными другим пользователем.
        Response responseDelNewUser2 = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuthNewUser1, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuthNewUser1, "auth_sid"))
                .delete("https://playground.learnqa.ru/api/user/" + userIdNew2)
                .andReturn();

        Assertions.assertResponseCodeEquals(responseDelNewUser2, 400);

        //Просмотр удаленного пользователя по id
        Response responseCheckDelNewUser1 = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/user/" + userIdNew1)
                .andReturn();

        responseCheckDelNewUser1.prettyPrint();

        //Просмотр авторизованного пользователя
        Response responseCheckDelNewUser2 = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/user/" + userIdNew2)
                .andReturn();

        responseCheckDelNewUser2.prettyPrint();



    }
}
