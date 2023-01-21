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
    // todo: actually implement current line and char logic
    private int curLine = 1;
    private int curChar = 1;

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

    public FoundToken nextToken() {
        boolean searchForSkippableChars = true;
        while (searchForSkippableChars) {
            searchForSkippableChars = false;
            // inline comment skip
            if (sourceIndex < sourceCode.length() - 1 && sourceCode.charAt(sourceIndex) == '/' && sourceCode.charAt(sourceIndex+1) == '/') {
                searchForSkippableChars = true;
                sourceIndex+=2;
                while (sourceIndex < sourceCode.length() && sourceCode.charAt(sourceIndex) != '\n')
                    sourceIndex++;
            }
            // block comment skip
            if (sourceIndex < sourceCode.length() -1 && sourceCode.charAt(sourceIndex) == '/' && sourceCode.charAt(sourceIndex+1) == '*') {
                searchForSkippableChars = true;
                int openers = 1;
                sourceIndex+=2;
                while (sourceIndex < sourceCode.length()) {
                    if (sourceIndex < sourceCode.length() - 1) {
                        if(sourceCode.charAt(sourceIndex) == '/' && sourceCode.charAt(sourceIndex+1) == '*') {
                            openers++;
                            sourceIndex++;
                        } else if (sourceCode.charAt(sourceIndex) == '*' && sourceCode.charAt(sourceIndex+1) == '/') {
                            openers--;
                            sourceIndex++;
                        }
                    }

                    if (openers == 0) {
                        sourceIndex+=2;
                        break;
                    }

                    sourceIndex++;
                }
            }
            // whitespace
            if (sourceIndex < sourceCode.length() && isWhiteSpace(sourceCode.charAt(sourceIndex))) {
                searchForSkippableChars = true;
                while (sourceIndex < sourceCode.length() && isWhiteSpace(sourceCode.charAt(sourceIndex))) {
                    sourceIndex++;
                }
            }
        }

        if (sourceIndex >= sourceCode.length())
            return new FoundToken(Token.END_OF_FILE, sourceCode.substring(sourceIndex - 1, sourceIndex - 1), curLine, curChar);

        int foundTokenStartIndex = sourceIndex;

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

        int foundTokenEndIndex = sourceIndex;

        if (cur.isTerminal())
            return new FoundToken(cur.getPathToken(), sourceCode.substring(foundTokenStartIndex, foundTokenEndIndex), curLine, curChar);

        // todo: do error handling
        sourceIndex++;
        return new FoundToken(Token.ERROR, sourceCode.substring(foundTokenStartIndex, foundTokenEndIndex), curLine, curChar);
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
