import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class HackPass {
    @Test
    public void testHuckPass(){
        //Парсинг пароля
        String link0 = "https://en.wikipedia.org/wiki/List_of_the_most_common_passwords";
        //tree = html.fromstring(response.text)
        //locator = '//*[contains(text(),"Top 25 most common passwords by year according to SplashData")]//..//td[@align="left"]/text()'
        //passwords = tree.xpath(locator)


        Map<String, String> params = new HashMap<String,String>();
        params.put("login", "asd");
        params.put("password", "asd");

        String link = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        Response response = RestAssured
                .given()
                .body(params)
                .post(link)
                .andReturn();
        response.prettyPrint();

        System.out.println(response.getStatusCode());

        String link2 = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    }
}

//Если вызвать метод без поля login или указать несуществующий login, метод вернет 500
//Если login указан и существует, метод вернет нам авторизационную cookie
// с названием auth_cookie и каким-то значением.

//Итак, наша задача - написать тест и указать в нем login нашего коллеги и все пароли
// из Википедии в виде списка. Программа должна делать следующее:

//Брать очередной пароль и вместе с логином коллеги вызывать первый метод get_secret_password_homework.
// В ответ метод будет возвращать авторизационную cookie с именем auth_cookie и каким-то значением.

//Далее эту cookie мы должна передать во второй метод check_auth_cookie.
// Если в ответ вернулась фраза "You are NOT authorized", значит пароль неправильный.
// В этом случае берем следующий пароль и все заново. Если же вернулась другая фраза - нужно,
// чтобы программа вывела верный пароль и эту фразу.