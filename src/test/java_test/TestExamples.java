//import com.sun.tools.javac.code.Attribute;
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

//import java.util.*;


public class TestExamples {
    @Test
    public void getJsonWithParams() {

        Response response;
        response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> authCookie = response.getCookies();
        System.out.println(authCookie);
        String getCookie = response.getCookie("HomeWork");
        System.out.println(getCookie);


/*
        String[] keys = {"email", "password", "username", "firstName", "lastName"};

        System.out.println(keys instanceof Object); //true
        System.out.println(keys);
*/





       /* Map<String, String> params = new HashMap<>();
        params.put("name", "Jhon");
        params.put("name1", "Alex");
        params.put("name2", "Vally");

        System.out.println(params.get("name2"));
        System.out.println(params.keySet());
        System.out.println(params.values());

       // Collection test = params.values();

        for(Map.Entry<String,String> x:params.entrySet()) {
            System.out.println(x.getKey()+" "+x.getValue());
       */
    }
}