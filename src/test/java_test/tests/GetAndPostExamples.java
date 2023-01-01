/*
package tests;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetAndPostExamples {
    @Test
    public void testGet(){
        //baseURI=""
        given()
                .get("https://playground.learnqa.ru/api/get_json")
                .then()
                .statusCode(200);
                //.body("", equalTo());
    }

    @Test
    public void testPost(){
        JSONObject request = new JSONObject();
        request.put("name", "Alex");
        System.out.println(request.toJSONString());

        given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("https://playground.learnqa.ru/api/hello")
                .then()
                .statusCode(200)
                .log().all();

    }
}
*/
