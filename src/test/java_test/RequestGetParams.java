
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import com.jayway.jsonpath.JsonPath;

import static io.restassured.RestAssured.given;


public class RequestGetParams {
    String link = "https://playground.learnqa.ru/api/hello";
    String name = "Alex";
    @Test
    public void getParamsForRequest(){
//        JsonPath beforeCookies = given()//given - статичный метода в ресташурд,
                // с него начинаются все запросы для http, его нужно импортировать
    }

}
