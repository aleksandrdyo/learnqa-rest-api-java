import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirect {
    @Test
    public void testLongRedirect(){

        String link = "https://playground.learnqa.ru/api/long_redirect";
        int redirectCount = 0;
        int status_code = 0;

        //for(int i = 0; i<5; i++)
        while(status_code!=200)
        {

            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(link);
                    //.andReturn();

            int get_status_code = response.getStatusCode();
            System.out.println("Статус кода - " + get_status_code);
            System.out.println("______");
            String locationHeader = response.getHeader("location");
            link=locationHeader;
            redirectCount=redirectCount+1;

            status_code = get_status_code;

            if (status_code==200){
                break;
            }

            System.out.println("URL - " + locationHeader);
            System.out.println("Редирект №-" + redirectCount);

        }
    }
}
