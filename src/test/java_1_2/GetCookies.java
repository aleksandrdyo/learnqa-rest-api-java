import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class GetCookies {
    @Test
    public void testGetCookies(){
        Map<String,String> data = new HashMap<>();
        data.put("login", "secret_login2");
        data.put("password", "secret_pass2");

        String link = "https://playground.learnqa.ru/api/get_auth_cookie";
        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post(link)
                .andReturn();
/*        System.out.println("\n Pretty text:");
        response.prettyPrint();

        System.out.println("\n Headers:");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\n Cookies:");
        Map<String,String> responseCookies = response.getCookies();
        System.out.println(responseCookies);*/

        //System.out.println(response.getStatusCode());

        String responseCookie = response.getCookie("auth_cookie");
        //System.out.println(responseCookie);

        String link2 = "https://playground.learnqa.ru/api/check_auth_cookie";

        Map<String, String> cookie = new HashMap<>();
        if (responseCookie!=null){
            cookie.put("auth_cookie", responseCookie);
        }

        Response response2 = RestAssured
                .given()
                .body(data)
                .cookies(cookie)
                .when()
                .post(link2)
                .andReturn();

        response2.print();

    }
}
