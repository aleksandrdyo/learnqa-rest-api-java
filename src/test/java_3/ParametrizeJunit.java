import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ParametrizeJunit {

    @ParameterizedTest//заменяет тег @Test
    @ValueSource(strings = {"","Jhon","Pete", "Sunny"})
    public void testHelloMethodWithoutName(String name){//в String name передаются параметризованные значения
        Map<String, String> queryParams = new HashMap<>();//пустой хешмап в котором будут хранится параметры

        if (name.length()>0){//если имя больше 0 то добавляем имя в параметр name, пустое имя не будет передоваться
            queryParams.put("name", name);
        }

        JsonPath response = RestAssured
                .given()
                .queryParams(queryParams)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        //response.prettyPrint();
        String answer = response.getString("answer");
        String expectdName = (name.length()>0 ? name: "someone");//строка с ожидаемым результом(тернарный оператор)
        assertEquals("Hello, "+expectdName, answer, "The answer is not expected");

    }

/*
    @Test
    public void testHelloMethodWithName(){
        String name = "Username";

        JsonPath response = RestAssured
                .given()
                .queryParam("name", name)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        response.prettyPrint();
        String answer = response.getString("answer");
        assertEquals( "Hello, " + name, answer, "The answer is not expected");

    }
*/

}
