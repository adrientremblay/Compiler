package syntactical_analysis;

import lexical_analysis.Token;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// todo: right know the grammar table is generated dynamically, implement saving and loading later
public class GrammarTableGenerator {
    HashMap<String, HashMap<Token, String>> grammarTable;
    HashMap<String, ArrayList<String[]>> rules;

    HashMap<String, ArrayList<String>> firstSets;
    HashMap<String, ArrayList<String>> followSets;

    public HashMap<String, HashMap<Token, String>> generateGrammarTable() {
        HashMap<String, HashMap<Token, String>> grammarTable = new HashMap<String, HashMap<Token, String>>();

        // Read the grammar file
        List<String> lines = Util.readFileForLines("grammar/grammar.grm");

        // Reading rules
        rules = new HashMap<String, ArrayList<String[]>>();

        for (String line : lines) {
            if (line.length() == 0)
                continue;

            String[] stringSplit = line.split(" ");

            if (stringSplit.length < 3 || stringSplit[0].length() == 0 || (!stringSplit[1].equals("->"))) {
                System.err.println("Invalid rule : " + line);
                continue;
            }

            String leftHandSide = stringSplit[0];
            String[] rightHandSide = Arrays.<String>copyOfRange(stringSplit, 2, stringSplit.length);

            if (!rules.containsKey(leftHandSide))
                rules.put(leftHandSide, new ArrayList<String[]>());

            rules.get(leftHandSide).add(rightHandSide);
        }

        // Generating first sets

        // Parse the grammar file


        return null;
    };

    private static boolean isTerminal(String s) {
        if (s.length() <= 2)
            return false;

        return (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'');
    }
}
