package lexical_analysis;

import dfa_generation.*;

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
        if (sourceIndex >= sourceCode.length())
            return Token.END_OF_FILE;

        while (isWhiteSpace(sourceCode.charAt(sourceIndex)))
            sourceIndex++;

        // inline comment skip
        while(sourceIndex < sourceCode.length() - 1 && sourceCode.charAt(sourceIndex) == '/' && sourceCode.charAt(sourceIndex+1) == '/') {
            sourceIndex+=2;
            while (sourceIndex < sourceCode.length() && sourceCode.charAt(sourceIndex) != '\n')
                sourceIndex++;
            sourceIndex++;
        }

        if (sourceIndex >= sourceCode.length())
            return Token.END_OF_FILE;

        DfaState cur = dfa;
        boolean foundNextState = true;
        do {
            char next = sourceCode.charAt(sourceIndex);

            foundNextState = false;

            for (State.Edge edge : cur.getEdges()) {
                if ((edge.label.equals(Token.LETTER.getName()) && isAlpha(next)) ||
                    (edge.label.equals(Token.DIGIT.getName()) && isDigit(next)) ||
                    (edge.label.equals(Token.NON_ZERO.getName()) && isNonZero(next)) ||
                    (edge.label.length() == 1 && edge.label.charAt(0) == next)) {
                    cur = (DfaState) edge.destination; // todo: cringe caused by my design of the states
                    foundNextState = true;
                    sourceIndex++;
                    break;
                }
            }
        } while (foundNextState && sourceIndex < sourceCode.length());

        if (cur.isTerminal())
            return cur.getPathToken();

        // todo: do error handling
        sourceIndex++;
        return Token.ERROR;
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

    private static boolean isWhiteSpace(char c) {
        return (c == ' ' || c == '\n' || c == '\t');
    }
}
