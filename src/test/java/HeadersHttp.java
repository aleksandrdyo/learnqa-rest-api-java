import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class HeadersHttp {
    @Test
    public void testHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeaders", "myValue");
        headers.put("myHeaders1", "myValue1");

        String link = "https://playground.learnqa.ru/api/check_type";
        String link1 = "https://playground.learnqa.ru/api/get_303";

        Response response = RestAssured
                .given()
                .headers(headers)
                .when()
                .get(link1)
                .andReturn();
        response.prettyPrint();//Получаем отправленные все заголовки

        Headers responseHeader = response.getHeaders();//получаем все заголовки от сервера
        System.out.println(responseHeader);


    }

    @Test
    public void testRedirects(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeaders", "myValue");
        headers.put("myHeaders1", "myValue1");

        String link = "https://playground.learnqa.ru/api/check_type";
        String link1 = "https://playground.learnqa.ru/api/get_303";

        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get(link1)
                .andReturn();
        response.prettyPrint();//Получаем отправленные все заголовки

        String locationHeader = response.getHeader("location");
        System.out.println(locationHeader);

    }
}

