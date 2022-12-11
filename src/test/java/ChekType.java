import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class ChekType {
    @Test
    public void testCheckTypeGet(){
        String link = "https://playground.learnqa.ru/api/check_type";

        Response response = RestAssured
                .given()
                .queryParams("param1", "value1")
                .queryParams("param2", "value2")
                .get(link)
                .andReturn();
        //response.prettyPrint();
        response.print();
    }

    @Test
    public void testCheckTypePost(){
        Map<String,String> body = new HashMap<>();
        body.put("param1", "value1");
        body.put("param2", "value2");

        String link = "https://playground.learnqa.ru/api/check_type";

        Response response = RestAssured
                .given()
                //.body("param1=value1&param2=value2")
                .body(body)
                .post(link)
                .andReturn();
        //response.prettyPrint();
        response.print();
    }
}
