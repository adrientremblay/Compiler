package syntactical_analysis;

import lexical_analysis.FoundToken;
import lexical_analysis.Lexer;
import lexical_analysis.Token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    Lexer lexer;

    HashMap<String, HashMap<String, String>> grammarTable;

    public Parser() {
        lexer = new Lexer();

        GrammarTableGenerator grammarTableGenerator = new GrammarTableGenerator();
        grammarTable = grammarTableGenerator.generateGrammarTable();
    }

    public boolean parse() {
        Stack<String> parseStack = new Stack<String>();

        parseStack.push("$");
        parseStack.push("START");

        FoundToken foundToken = lexer.nextToken();
        String top;
        while ((top = parseStack.peek()) != "$") { // todo: can you do this in Java?
            if (GrammarTableGenerator.isTerminal(top)) {
                if (top.substring(1, top.length() - 1).equals(foundToken.getToken().getName())) {
                    // found a terminal
                    parseStack.pop();
                    foundToken = lexer.nextToken();
                } else {
                    // did not find the terminal I wanted to... :'(
                    System.err.println("ERROR!");
                }
            } else {
                if (grammarTable.containsKey(top)) {
                    if (grammarTable.get(top).containsKey(foundToken.getToken().getName())) {
                        String rule = grammarTable.get(top).get(foundToken.getToken().getName());
                        String[] ruleSplit = rule.split(" ");

                        String[] rightHandSide = Arrays.<String>copyOfRange(ruleSplit, 2, ruleSplit.length);
                        for (int i = rightHandSide.length - 1 ; i > 0 ; i--) {
                            parseStack.push(rightHandSide[i]);
                        }
                    } else {
                       // The cell is empty...
                       System.err.println("ERROR!");
                    }
                } else {
                    // The rule isnt' even in the parse table???
                    System.err.println("ERROR!");
                }
            }
        }

        if (foundToken.getToken() != Token.END_OF_FILE)
            return false;

        return true;
    }

    public void loadSource(String source) {
        lexer.loadSource(source);
    }
}
