package syntactical_analysis;

import lexical_analysis.Token;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// todo: right know the grammar table is generated dynamically, implement saving and loading later
public class GrammarTableGenerator {
    HashMap<String, HashMap<Token, String>> grammarTable;
    ArrayList<HashMap<String, ArrayList<String>>> rules;

    public HashMap<String, HashMap<Token, String>> generateGrammarTable() {
        HashMap<String, HashMap<Token, String>> grammarTable = new HashMap<String, HashMap<Token, String>>();

        // Read the grammar file
        List<String> lines = Util.readFileForLines("grammar/grammar.grm");

        // Reading rules
        for (String line : lines) {
            System.out.println(line);
        }

        // Parse the grammar file


        return null;
    };

    private static void handleRule(HashMap<String, HashMap<Token, String>> grammarTable, String rule) {

    }
}
