import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SendToken{
    @Test
    public void testGetToken() throws InterruptedException {
        String link = "https://playground.learnqa.ru/ajax/api/longtime_job";

        Map<String, String> token = new HashMap<>();
        token.put("token", "gNyoDO0oDMyASMx0iMx0iMyAjM");

        JsonPath response_status = RestAssured
                .given()
                .queryParams(token)
                .get(link)
                .jsonPath();
        //response_status.prettyPrint();

        String status = response_status.get("status");
        String result = response_status.get("result");


        System.out.println(status);
        System.out.println(result);

/*        int status_code = response_status.getStatusCode();
        System.out.println(status_code);*/
    }
}
