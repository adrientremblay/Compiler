import java.util.ArrayList;
import java.util.HashMap;

public class Driver {
    public static void main(String args[]) {
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("id", "letter alphanum*");
        testMap.put("alphanum", "letter | digit | _");
        testMap.put("digit", "0.009");

        ArrayList<String> testTokens = new ArrayList<String>();
        testTokens.add("id");
    }
}
