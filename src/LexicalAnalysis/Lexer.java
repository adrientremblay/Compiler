package LexicalAnalysis;

import DfaGeneration.*;

import java.io.IOException;

/**
 * Actually a stateful class
 */
public class Lexer {
    private NdfaGenerator ndfaGenerator;
    private DfaState dfa;
    private String sourceCode;
    private int sourceIndex;

    public Lexer() throws IOException {
        ndfaGenerator = new NdfaGenerator();
        State ndfa = ndfaGenerator.generate();
        AutomataVisualizer.visualize(ndfa, "ndfa");

        dfa = NdfaToDfaConvertor.convert(ndfa);
        AutomataVisualizer.visualize(dfa, "dfa");
    }

    public void loadSource(String source) {
        this.sourceCode = source;
        sourceIndex = 0;
    }

    public Token nextToken() {
        DfaState cur = dfa;

        while (!cur.isTerminal()) {
            char next = nextChar();
            boolean foundNextState = false;

            for (State.Edge edge : cur.getEdges()) {
                if ((edge.label.equals(Token.LETTER.getName()) && isAlpha(next)) ||
                    (edge.label.equals(Token.DIGIT.getName()) && isDigit(next)) ||
                    (edge.label.equals(Token.NON_ZERO.getName()) && isNonZero(next)) ||
                    (edge.label.length() == 1 && edge.label.charAt(0) == next)) {
                    cur = (DfaState) edge.destination; // todo: cringe caused by my design of the states
                    foundNextState = true;
                    break;
                }
            }

            if (!foundNextState) {
                return null;
            }
        }

        return cur.getPathToken();
    }

    private char nextChar() {
        return sourceCode.charAt(sourceIndex++);
    }

    private char peek() {
        return sourceCode.charAt(sourceIndex++);
    }

    private static boolean isAlpha(char c) {
        if (65 <= c && c <= 90)
            return true;

        if (97 <= c && c <= 122)
            return true;

        return false;
    }

    private static boolean isDigit(char c) {
        if (48 <= c && c <= 57)
            return true;

        return false;
    }

    private static boolean isNonZero(char c) {
        if (48 < c && c <= 57)
            return true;

        return false;
    }
}
