import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class RedirectLink {
    @Test
    public void testStatusCode(){
        String link = "https://playground.learnqa.ru/api/check_type";
        String link1 = "https://playground.learnqa.ru/api/get_303";
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get(link1)
                .andReturn();

        int status_code = response.getStatusCode();
        System.out.println(status_code);

    }
}

