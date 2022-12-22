import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestHeader {
    @Test
    public void testRequestHeader(){
        String link = "https://playground.learnqa.ru/api/homework_header";

        Response response = RestAssured
                .get(link)
                .andReturn();
        Headers headers = response.getHeaders();
        //System.out.println(headers);

        //String getHeader = response.getHeader("x-secret-homework-header");
        //System.out.println(getHeader);

        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"),
                "Response doesn't have x-secret-homework-header header");

    }
}
