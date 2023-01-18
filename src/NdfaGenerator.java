import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Takes a regular expression and generates a NDFA
 */
public class NdfaGenerator {
    private HashMap<String, String> atomicLexicalElementMap;
    private ArrayList<String> tokens;
    private State root;

    public NdfaGenerator(HashMap<String, String> atomicLexicalElementMap, ArrayList<String> tokens) {
        this.atomicLexicalElementMap = atomicLexicalElementMap;
        this.tokens = tokens;
        root = new State();
    }

    public void generate() {
       for  (String token : tokens)
            generateNdfaFromElement(token);
    }

    private Map.Entry<State, State> generateNdfaFromElement(String element) {
        // checking if we can split up the element
        String[] elementSplit = element.split(" ");

        if (elementSplit.length == 1) {
            if (element.charAt(element.length() - 1) == '*')  {
                // recursive thingy
                State start = new State();

            } else {
                State start = new State();
                State end = new State();
                start.addEdge(element, end);
                return new Map.Entry<String, String>(start, end);
            }
        }

        ArrayList<String> regularExpression = new ArrayList<String>(Arrays.asList(atomicLexicalElementMap.get(element).split(" ")));

        // step 1: processing to expand the regular expression into atomic elements
        int i = 0;
        while (i < regularExpression.size()) {
            String item = regularExpression.get(i);

            if (atomicLexicalElementMap.containsKey(item)) { // expand it
                regularExpression.remove(i);
                regularExpression.add(i, atomicLexicalElementMap.get(item));
            }



        }
    }
}
