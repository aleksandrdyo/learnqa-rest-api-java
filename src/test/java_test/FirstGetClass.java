//package tests;
import io.restassured.RestAssured;
//import org.junit.Test;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class FirstGetClass {
    //String link = "https://playground.learnqa.ru/api/hello";
    String link = "https://playground.learnqa.ru/api/get_500";

    @Test
    public void firstGetTest(){
        RestAssured.
                when().get(link).//отправляется Get запрос на указанный URL.
                then().assertThat().statusCode(500);//валидирует, что мы получили 200 код, и значит все прошло успешно.
    }

}
