import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class GetParams {

    @Test
    public void getJsonWithParams(){
        Map<String, String> params = new HashMap<>();
        params.put("name", "Jhon");

        Response response = RestAssured
                .given()
//                .queryParam("name", "Jhon")
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void JsonPars(){
        Map<String, String> params = new HashMap<>();
        params.put("name", "Jhon");

        JsonPath response1 = RestAssured
                .given()
//                .queryParam("name", "Jhon")
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        response1.prettyPrint();

        String name = response1.get("answer");
        if (name == null){
            System.out.println("the key answer2 is absent");
        }else {
            System.out.println(name);
        };

    }

}
