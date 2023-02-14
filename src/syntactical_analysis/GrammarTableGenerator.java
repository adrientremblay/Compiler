package syntactical_analysis;

import lexical_analysis.Token;
import util.Util;

import java.util.HashMap;
import java.util.Map;

// todo: right know the grammar table is generated dynamically, implement saving and loading later
public class GrammarTableGenerator {
    public static HashMap<String, HashMap<Token, String>> generateGrammarTable() {
        HashMap<String, HashMap<Token, String>> grammarTable = new HashMap<String, HashMap<Token, String>>();

        String grammarString = Util.readFileAsString("grammar/grammar.grm");

        System.out.println(grammarString);

        return null;
    };
}
