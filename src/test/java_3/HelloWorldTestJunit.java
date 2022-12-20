import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//01. JUnit и простые тесты
public class HelloWorldTestJunit {
    @Test
    public void testRestAssuredJunit(){
        String link = "https://playground.learnqa.ru/api/map";
        Response response = RestAssured
                .get(link)
                .andReturn();
        //assertTrue(response.statusCode() == 300, "unexpected status code");
        assertEquals(200, response.statusCode(), "unexpected status code");
    }

    @Test
    public void testRestAssuredJunit2(){
        String link = "https://playground.learnqa.ru/api/map";
        Response response = RestAssured
                .get(link)
                .andReturn();
        //assertTrue(response.statusCode() == 300, "unexpected status code");
        assertEquals(200, response.statusCode(), "unexpected status code");
    }
}
