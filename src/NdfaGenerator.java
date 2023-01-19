import java.util.ArrayList;
import java.util.HashMap;

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

    public State generate() {
        // generate an Ndfa for every token
        ArrayList<State> generatedTokenEntryStates = new ArrayList<State>();
        for  (String token : tokens) {
            Ndfa newNdfa = generateNdfaFromElement(token);
            generatedTokenEntryStates.add(newNdfa.getEntry());
        }

        // connect them and return the Ndfa
        State start = new State();
        for (State entryState : generatedTokenEntryStates) {
            start.addEdge("ε", entryState);
        }

        return start;
    }

    private Ndfa generateNdfaFromElement(String element) {
        // checking if we can split up the element
        String[] elementSplit = splitIgnoreBrackets(element);

        if (elementSplit.length == 1) {
            // There is only a single element
            if (element.charAt(element.length() - 1) == '*')  {
                // recursive thingy
                State start = new State();
                State end = new State();
                start.addEdge("ε", end);

                Ndfa repeatedPart = generateNdfaFromElement(element.substring(0, element.length() - 1));
                repeatedPart.getExit().addEdge("ε", repeatedPart.getEntry());

                start.addEdge("ε", repeatedPart.getEntry());
                repeatedPart.getExit().addEdge("ε", end);

                return new Ndfa(start, end);
            } else if (element.charAt(0) == '[' && element.charAt(element.length() - 1) == ']') {
                // element is in square brackets. The point of this is to isolate recursions.
                return generateNdfaFromElement(element.substring(1, element.length() - 1));
            } else if (atomicLexicalElementMap.containsKey(element)) {
                // Can expand element from map
                return generateNdfaFromElement(atomicLexicalElementMap.get(element));
            } else {
                // element cannot be expanded
                State start = new State();
                State end = new State();
                start.addEdge(element, end);
                return new Ndfa(start, end);
            }
        }

        // construct a combination of elements
        State start = new State();
        State cur = start;
        ArrayList<State> tails = new ArrayList<State>();
        for (int i = 0 ; i < elementSplit.length ; i++) {
            Ndfa pathNdfa = generateNdfaFromElement(elementSplit[i]);
            if (i < elementSplit.length - 1 && elementSplit[i+1].equals("|")) {
                // create new branch from start
                i++;

                if (cur != start)
                    tails.add(cur);

                start.addEdge("ε", pathNdfa.getEntry());
                cur = pathNdfa.getExit();
            } else {
                // continue along whatever branch you're at (or the start)
                cur.addEdge("ε", pathNdfa.getEntry());
                cur = pathNdfa.getExit();
            }
        }

        State end = new State();
        cur.addEdge("ε", end);
        for (State tail : tails)
            tail.addEdge("ε", end);

        return new Ndfa(start, end);
    }

    private String[] splitIgnoreBrackets(String s) {
        int left = 0;

        ArrayList<String> ret = new ArrayList<String>();

        int howManyEndBracketsAmIHunting  = 0;
        for (int right = 0 ; right < s.length() ; right++) {
            if (s.charAt(right) == '[') {
                howManyEndBracketsAmIHunting++;
            }  else if (s.charAt(right) == ']') {
                howManyEndBracketsAmIHunting--;
            } else if (howManyEndBracketsAmIHunting > 0) {
                // sigma -> do nothing
            } else if (s.charAt(right) == ' ') {
                ret.add(s.substring(left, right));
                left = right+1;
            }
        }

        ret.add(s.substring(left, s.length()));

        String[] retArr = new String[ret.size()];
        ret.toArray(retArr);
        return retArr;
    }
}
