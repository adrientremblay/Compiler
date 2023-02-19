package syntactical_analysis;

import lexical_analysis.FoundToken;
import lexical_analysis.Lexer;
import lexical_analysis.Token;
import util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    private Lexer lexer;

    private HashMap<String, HashMap<String, String>> grammarTable;
    private SyntaxDerivationPrinter syntaxDerivationPrinter;


    public Parser() {
        lexer = new Lexer();

        GrammarTableGenerator grammarTableGenerator = new GrammarTableGenerator();
        grammarTable = grammarTableGenerator.generateGrammarTable();
    }

    public boolean parse() {
        System.out.println("Starting Parse.");

        Stack<String> parseStack = new Stack<String>();

        parseStack.push("$");
        parseStack.push("START");

        FoundToken foundToken = lexer.nextToken();
        String top;
        while ((top = parseStack.peek()) != "$") {
            // skips
            if (foundToken.getToken() == Token.IN_LINE_COMMENT || foundToken.getToken() == Token.BLOCK_COMMENT) {
                foundToken = lexer.nextToken();
                continue;
            }

            if (top.equals("EPSILON")) {
                parseStack.pop();
                continue;
            }

            if (GrammarTableGenerator.isTerminal(top)) {
                String topTerminal = top.substring(1, top.length() - 1);
                if (
                    topTerminal.equals(foundToken.getToken().getName()) // for reserved words
                    || topTerminal.equals(foundToken.getToken().getRegex()) // for everything else
                    || (topTerminal.equals("id") && foundToken.getToken().isType()) // type tokens should count as identifiers too
                ) {
                    // found a terminal
                    //System.out.println("DEBUG: FOUND " + foundToken.getLexeme());
                    parseStack.pop();
                    foundToken = lexer.nextToken();
                } else {
                    // did not find the terminal I wanted to... :'(
                    System.err.println("ERROR!");
                    break;
                }
            } else {
                if (grammarTable.containsKey(top)) {
                    String rule;
                    if (grammarTable.get(top).containsKey(foundToken.getToken().getName())) {
                        rule = grammarTable.get(top).get(foundToken.getToken().getName());
                    } else if (grammarTable.get(top).containsKey(foundToken.getToken().getRegex())) { // todo: CRINGE!!
                        rule = grammarTable.get(top).get(foundToken.getToken().getRegex());
                    } else if (foundToken.getToken().equals(Token.END_OF_FILE) && grammarTable.get(top).containsKey("$")) {
                        rule = grammarTable.get(top).get("$");
                    } else {
                       // The cell is empty...
                       System.err.println("ERROR!");
                       break;
                    }

                    parseStack.pop();

                    syntaxDerivationPrinter.writeLine(rule);

                    String[] ruleSplit = rule.split(" ");

                    String[] rightHandSide = Arrays.<String>copyOfRange(ruleSplit, 2, ruleSplit.length);
                    for (int i = rightHandSide.length - 1 ; i >= 0 ; i--) {
                        parseStack.push(rightHandSide[i]);
                    }
                } else {
                    // The rule isnt' even in the parse table???
                    System.err.println("ERROR!");
                    break;
                }
            }
        }

        if (foundToken.getToken() != Token.END_OF_FILE)
            return false;

        System.out.println("Finished Parse.");

        return true;
    }

    public void loadSource(String sourceFilePath) {
        lexer.loadSource(Util.readFileAsString(sourceFilePath));
        syntaxDerivationPrinter = new SyntaxDerivationPrinter(sourceFilePath);
    }
}
