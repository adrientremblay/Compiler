import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Driver {
    public static void main(String args[]) throws IOException {
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("id", "letter alphanum*");
        testMap.put("alphanum", "letter | digit | _");
        testMap.put("integer", "[nonzero digit*] | 0");
        testMap.put("float", "integer fraction [e [+ | -] integer]");
        testMap.put("fraction", ". [digit* nonzero] | 0");

        ArrayList<String> testTokens = new ArrayList<String>();
        testTokens.add("id");
        testTokens.add("integer");
        testTokens.add("float");

        NdfaGenerator ndfaGenerator = new NdfaGenerator(testMap, testTokens);
        State entry = ndfaGenerator.generate();

        AutomataVisualizer automataVisualizer = new AutomataVisualizer();
        automataVisualizer.visualize(entry);
    }
}
