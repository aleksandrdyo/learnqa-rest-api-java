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

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserAgentCheck {

    final String UA1 = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    final String UA2 = "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1";
    final String UA3 = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    final String UA4 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0";
    final String UA5 = "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";

    @ParameterizedTest
    @ValueSource(strings = {UA1, UA2, UA3, UA4, UA5})
    public void testUserAgent(String name) {
        Map<String, String> useragent = new HashMap<>();
        useragent.put("User-Agent", name);

        String link = "https://playground.learnqa.ru/ajax/api/user_agent_check";

        JsonPath response = RestAssured
                .given()
                .headers(useragent)
                .get(link)
                .jsonPath();

        //response.prettyPrint();

        String responseUseragent = response.getString("user_agent");
        String responsePlatform = response.getString("platform");
        String responseBrowser = response.getString("browser");
        String responseDevice = response.getString("device");

//        System.out.println(responseUseragent);

        if (responseUseragent.equals("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")) {
            assertEquals(responsePlatform, "Mobile", "incorrect user_agent " + UA1);
            assertEquals(responseBrowser, "No", "incorrect user_agent " + UA1);
            assertEquals(responseDevice, "Android", "incorrect user_agent " + UA1);
        } else if (responseUseragent.equals("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1")) {
            assertEquals(responsePlatform, "Mobile", "incorrect user_agent " + UA2);
            assertEquals(responseBrowser, "Chrome", "incorrect user_agent " + UA2);
            assertEquals(responseDevice, "iOS", "incorrect user_agent " + UA2);
        } else if (responseUseragent.equals("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")) {
            assertEquals(responsePlatform, "Googlebot", "incorrect user_agent " + UA3);
            assertEquals(responseBrowser, "Unknown", "incorrect user_agent " + UA3);
            assertEquals(responseDevice, "Unknown", "incorrect user_agent " + UA3);
        } else if (responseUseragent.equals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0")) {
            assertEquals(responsePlatform, "Web", "incorrect user_agent " + UA4);
            assertEquals(responseBrowser, "Chrome", "incorrect user_agent " + UA4);
            assertEquals(responseDevice, "No", "incorrect user_agent " + UA4);
        } else if (responseUseragent.equals("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")) {
            assertEquals(responsePlatform, "Mobile", "incorrect user_agent " + UA5);
            assertEquals(responseBrowser, "No", "incorrect user_agent " + UA5);
            assertEquals(responseDevice, "iPhone", "incorrect user_agent " + UA5);
        }

    }
}

