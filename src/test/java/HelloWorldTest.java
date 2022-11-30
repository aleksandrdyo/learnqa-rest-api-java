import io.restassured.RestAssured;//импорт класса RestAssured
import io.restassured.response.Response;//импорт класса
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    @Test
    public void testHelloWorld(){

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/hello")//создание get запроса на нужный апи
                .andReturn();//возврат результата
        response.prettyPrint();//вывод текста ответа в удобном формате

    }
}
