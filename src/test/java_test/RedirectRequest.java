/*
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class RedirectRequest {

    @Test
    public void testRedirect(){
        given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .when()
                .redirects().follow(false)
                .get("https://playground.learnqa.ru/api/long_redirect")
                .then()
                .statusCode(301);
                //.log().all();
                //.header("Location", notNullValue());
    }
}
*/
