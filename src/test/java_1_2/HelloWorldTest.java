import io.restassured.RestAssured;//импорт класса RestAssured
import io.restassured.response.Response;//импорт класса
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    @Test
    public void testHelloWorld(){
        String link = "https://playground.learnqa.ru/api/hello";
        //String link = "https://playground.learnqa.ru/api/get_500";


        Response response = RestAssured
                .get(link)//создание get запроса на нужный апи
                .andReturn();//возврат результата
        response.prettyPrint();//вывод текста ответа в удобном формате
        int code = response.getStatusCode();//получаем статус кода
        System.out.println(code);

        //Assert.assertEquals(code, 200);

    }
}
