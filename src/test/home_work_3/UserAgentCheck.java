import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.entity.mime.Header;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAgentCheck {

    final String UA1 = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    final String UA2 = "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1";
    final String UA3 = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    final String UA4 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0";
    final String UA5 = "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";


/*    private static final Map<String, String> useragents = new HashMap<>();
    static {
        useragents.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        useragents.put("User-Agent", "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1");
        useragents.put("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
        useragents.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0");
        useragents.put("User-Agent", "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
    }*/

    @ParameterizedTest
    @ValueSource(strings = {UA1, UA2, UA3, UA4, UA5})
    public void testUserAgent(String name) {
        Map<String, String> useragent = new HashMap<>();
        useragent.put("User-Agent", name);

        String link = "https://playground.learnqa.ru/ajax/api/user_agent_check";

        Response response = RestAssured
                .given()
                .headers(useragent)
                .get(link)
                .andReturn();

        response.prettyPrint();




        /*Map<String, String> exval = new HashMap<>();
        exval.put("platform", "Mobile");
        exval.put("browser", "No");
        exval.put("device", "Android");

        System.out.println(exval);
*/
    }
}



//'platform': 'Mobile', 'browser': 'No', 'device': 'Android'
//'platform': 'Mobile', 'browser': 'Chrome', 'device': 'iOS'
//'platform': 'Googlebot', 'browser': 'Unknown', 'device': 'Unknown'
//'platform': 'Web', 'browser': 'Chrome', 'device': 'No'
//'platform': 'Mobile', 'browser': 'No', 'device': 'iPhone'