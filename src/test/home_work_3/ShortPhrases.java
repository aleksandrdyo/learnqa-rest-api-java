import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShortPhrases {
    @Test
    public void testShortPhrases(){
        int count = 15;
        String shortphrase = "1234567890123456";
        //Scanner inputphrase = new Scanner(System.in);
        //String shortphrase = inputphrase.nextLine();
        //System.out.println(shortphrase);
        int countshortphrase = shortphrase.length();

        assertTrue(countshortphrase > count, "phrase is more then 15 symbol");

    }

}
