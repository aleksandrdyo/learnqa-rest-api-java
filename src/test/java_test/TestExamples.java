import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestExamples {

    @Test
    public void getJsonWithParams() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Jhon");
        params.put("name1", "Alex");
        params.put("name2", "Vally");

        System.out.println(params.get("name2"));
        System.out.println(params.keySet());
        System.out.println(params.values());

       // Collection test = params.values();

        for(Map.Entry<String,String> x:params.entrySet()) {
            System.out.println(x.getKey()+" "+x.getValue());
        }
    }
}