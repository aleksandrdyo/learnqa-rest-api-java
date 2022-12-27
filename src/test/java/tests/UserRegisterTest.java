package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {
    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.ru";

        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate = DataGenerator.getRegistrationData(userDate);
/*        userDate.put("username", "learnqa");
        userDate.put("firstName", "learnqa");
        userDate.put("lastName", "learnqa");
        userDate.put("password", "123");*/

        String link = "https://playground.learnqa.ru/api/user/";

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);

    }

    @Test
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getRandomEmail();

        //Map<String, String> userDate = new HashMap<>();
        Map<String, String> userDate = DataGenerator.getRegistrationData();

 /*       userDate.put("username", "learnqa");
        userDate.put("firstName", "learnqa");
        userDate.put("lastName", "learnqa");
        userDate.put("email", email);
        userDate.put("password", "123");*/

        String link = "https://playground.learnqa.ru/api/user/";

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .andReturn();

        System.out.println(responseCreateAuth.asString());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    //Создание пользователя с некорректным email - без символа @
    @Test
    public void testCreateUserUnsuccessfully() {
        String email = "vinkotovexample.ru";
        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate = DataGenerator.getRegistrationData(userDate);

        String link = "https://playground.learnqa.ru/api/user/";

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .andReturn();

        //responseCreateAuth.prettyPrint();
        //System.out.println(responseCreateAuth.getStatusCode());

        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    //Создание пользователя с очень коротким именем в один символ
    @Test
    public void testCreateUserUnsuccessfullyShortEmail() {
        String email = "x";
        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate = DataGenerator.getRegistrationData(userDate);

        String link = "https://playground.learnqa.ru/api/user/";

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .andReturn();

        //responseCreateAuth.prettyPrint();
        //System.out.println(responseCreateAuth.getStatusCode());

        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'email' field is too short");
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    //Создание пользователя с очень длинным именем - длиннее 250 символов
    @Test
    public void testCreateUserUnsuccessfullyLongEmail() {
        String firstPartOfEmail = "";
        String secondPartOfEmail = "@example.ru";
        int countSecongPart = secondPartOfEmail.length();
        int countFirstPart = 251 - countSecongPart;

        for(int i = 0; i<countFirstPart; i++){
            firstPartOfEmail = firstPartOfEmail + "i";
        }

        String email = firstPartOfEmail + secondPartOfEmail;

        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate = DataGenerator.getRegistrationData(userDate);

        String link = "https://playground.learnqa.ru/api/user/";

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'email' field is too long");
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }


    //- Создание пользователя без указания одного из полей - с помощью @ParameterizedTest
    // необходимо проверить, что отсутствие любого параметра не дает зарегистрировать пользователя
    @ParameterizedTest
    @ValueSource(strings = {"username", "lastName", "firstName"})
    //@MethodSource("fieldName")
    public void testCreateUserUnsuccessfullyWithoutOneField(String params, String name) {

        Map<String, String> userDate = new HashMap<>();
        userDate.put(params, name);
        userDate = DataGenerator.getRegistrationDataWithoutOneField(userDate);

        String link = "https://playground.learnqa.ru/api/user/";

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post(link)
                .andReturn();

        responseCreateAuth.prettyPrint();
        //System.out.println(responseCreateAuth.asString());
        //System.out.println(responseCreateAuth.statusCode());


        //Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        //Assertions.assertJsonHasField(responseCreateAuth, "id");
    }



}


