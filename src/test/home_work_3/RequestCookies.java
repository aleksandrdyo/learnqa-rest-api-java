import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestCookies {
    @Test
    public void testRequestCookies(){
        String link = "https://playground.learnqa.ru/api/homework_cookie";
        Response response = RestAssured
                .get(link)
                .andReturn();

        Map<String, String> cookies = response.getCookies();
        //System.out.println(cookies);
        assertTrue(cookies.containsKey("HomeWork"), "Response doesn't have HomeWork cookie");

        String getCookie = response.getCookie("HomeWork");
        //System.out.println(getCookie);


    }
}

//    Этот метод возвращает какую-то cookie с каким-то значением.
//    Необходимо понять что за cookie и с каким значением, и зафиксировать это поведение с помощью assert.