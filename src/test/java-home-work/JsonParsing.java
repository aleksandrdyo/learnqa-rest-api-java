import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.util.*;


public class JsonParsing {
    @Test
    public void jsonParsingTest(){
        String link = "https://playground.learnqa.ru/api/get_json_homework";

        JsonPath response = RestAssured
                .given()
                .get(link)
                .jsonPath();
        //response.prettyPrint();

        List jsonpars  = response.get("messages.message");
        System.out.println(jsonpars.get(1));

        //String message = response.getString("messages[1]");
        //System.out.println(message);

        /*for(int i = 0; i<jsonpars.size(); i++) {
            System.out.println(jsonpars.get(i));
        }*/
    }
}
