import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FirstRedirect {
    @Test
    public void testRedirect(){
        String link = "https://playground.learnqa.ru/api/long_redirect";
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(link)
                .andReturn();
        response.prettyPrint();

        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}


//    С этого адреса должен происходит редирект на другой адрес.
//    Наша задача — распечатать адрес, на который редиректит указанные URL.