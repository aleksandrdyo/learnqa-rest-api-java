package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
import lib.DataGenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


@Epic("User edit cases(Epic)")
@Feature("Edit(Feature)")
public class UserEditTest extends BaseTestCase {

    @Test
    @Link("https://mail.ru")
    @Issue("123")
    @TmsLink("test-1")
    @Severity(SeverityLevel.CRITICAL)
    @Attachment(value = "Page screenshot", type = "image/png")
    @Description("Positive test of successfully edit user(Description)")
    @DisplayName("Test positive edit user(DisplayName)")
    public void testEditJustCreatedTest() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");
        //System.out.println("posit " + userId);

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        //System.out.println(authData);

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Edit
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //Get - получаем данные юзера с куками и хедером
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        Assertions.assertJsonByName(responseUserData, "firstName", newName);
    }

    //Ex17: Негативные тесты на PUT
    @Test
    @Link("https://mail.ru")
    @Issue("123")
    @TmsLink("test-1")
    @Severity(SeverityLevel.MINOR)
    @Attachment(value = "Page screenshot", type = "image/png")
    @Description("Negative tests of edit user data")
    @DisplayName("Tests negative edit user data")
    public void testEditNegative() {
        //Generate User
        Map<String, String> userData1 = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData1)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId1 = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData1.get("email"));
        authData.put("password", userData1.get("password"));

        Response responseGetAuth1 = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Generate Other User With Other Id-
        Map<String, String> userDataOther = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuthOther = RestAssured
                .given()
                .body(userDataOther)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userIdOther = responseCreateAuthOther.getString("id");

        //Login Other User With Other Id-
        Map<String, String> authData1 = new HashMap<>();
        authData1.put("email", userDataOther.get("email"));
        authData1.put("password", userDataOther.get("password"));

        Response responseGetAuth2 = RestAssured
                .given()
                .body(authData1)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();


        //- Попытаемся изменить данные пользователя, будучи неавторизованными
        //Edit
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUserWithoutAuth = RestAssured
                .given()
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId1)
                .andReturn();

        Assertions.assertResponseTextEquals(responseEditUserWithoutAuth, "Auth token not supplied");
        Assertions.assertResponseCodeEquals(responseEditUserWithoutAuth, 400);

        //- Попытаемся изменить данные пользователя, будучи авторизованными другим пользователем
        //Edit
        String newNameOther = "Changed Name123";
        Map<String, String> editDataOther = new HashMap<>();
        editDataOther.put("firstName", newNameOther);

        Response responseEditOtherUser1 = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth1, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth1, "auth_sid"))
                .body(editDataOther)
                .put("https://playground.learnqa.ru/api/user/"+userIdOther)
                .andReturn();

        responseEditOtherUser1.prettyPrint();
        //Assertions.assertResponseTextEquals(responseEditOtherUser1, "");
        Assertions.assertResponseCodeEquals(responseEditOtherUser1, 400);

        //- Попытаемся изменить данные пользователя, будучи авторизованными другим пользователем
        //проверка на изменение данных
        Response responseUserData2 = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth1, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth1, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId1)
                .andReturn();

        //- Попытаемся изменить email пользователя, будучи авторизованными тем же пользователем,
        // на новый email без символа @
        //Edit
        String newEmail = "vinkotovexample.ru";
        Map<String, String> editEmail = new HashMap<>();
        editEmail.put("email", newEmail);

        Response responseEditUserWithIncorrectEmail = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth2, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth2, "auth_sid"))
                .body(editEmail)
                .put("https://playground.learnqa.ru/api/user/" + userIdOther)
                .andReturn();

        Assertions.assertResponseTextEquals(responseEditUserWithIncorrectEmail, "Invalid email format");
        Assertions.assertResponseCodeEquals(responseEditUserWithIncorrectEmail, 400);

        //- Попытаемся изменить firstName пользователя, будучи авторизованными тем же пользователем,
        // на очень короткое значение в один символ
        String newShortName = "v";
        Map<String, String> editFname = new HashMap<>();
        editFname.put("firstName", newShortName);

        Response responseEditUserWithShortName = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth2, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth2, "auth_sid"))
                .body(editFname)
                .put("https://playground.learnqa.ru/api/user/" + userIdOther)
                .andReturn();

        Assertions.assertJsonByName(responseEditUserWithShortName,"error", "Too short value for field firstName");
        Assertions.assertResponseCodeEquals(responseEditUserWithIncorrectEmail, 400);

    }
}
