import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
//import org.junit.Test;
//import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;


public class QueryParams {
    String name = "Aleksandr";
    String link = "https://playground.learnqa.ru/api/hello";
    static String json = "...";

    @Test
    public void queryParameter() {

                //.statusCode(200)
                //.log().all();

    }

    @Test
    public void queryTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                //.log().all()
                .get(link);
        response.prettyPrint();

        JsonPath extractor = response.jsonPath();
        String jsonparsing = extractor.get("answer");
        System.out.println(jsonparsing);
    }



    @Test
    public void queryTestArray(){
        //String res = JSONObject.toJSONString("asd":"asd", "asdfasd":"asf");
        //JsonPath extractor1 = res.jsonPath();
        //String text1 = extractor1.get("answer");
        //System.out.println(extractor1);
        //System.out.println(text1);
    }


}
