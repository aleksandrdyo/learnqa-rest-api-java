import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;


public class GetToken extends Thread{
    @Test
    public void testGetToken() throws InterruptedException{

        //1) создание задачи
        String link = "https://playground.learnqa.ru/ajax/api/longtime_job";
        JsonPath response = RestAssured
                .given()
                .get(link)
                .jsonPath();
        //response.prettyPrint();
        String get_token  = response.get("token");

        //2) делал один запрос с token ДО того, как задача готова, убеждался в правильности поля status
        Map<String, String> token = new HashMap<>();
        token.put("token", get_token);
        JsonPath response_status = RestAssured
                .given()
                .queryParams(token)
                .get(link)
                .jsonPath();
        //response_status.prettyPrint();
        String status_before = response_status.get("status");
        System.out.println("Status before - " + status_before);

        //3) ждал нужное количество секунд с помощью функции .sleep()
        Thread.sleep(20000);

        //4) делал бы один запрос c token ПОСЛЕ того, как задача готова,
        // убеждался в правильности поля status и наличии поля result
        JsonPath response_with_token = RestAssured
                .given()
                .queryParams(token)
                .get(link)
                .jsonPath();

        String status = response_with_token.get("status");
        String result = response_with_token.get("result");
        System.out.println("Result after - "+status);
        System.out.println("Status after - "+result);
    }
}


