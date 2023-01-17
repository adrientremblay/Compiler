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
    }
}
