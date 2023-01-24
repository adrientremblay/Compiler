package lexical_analysis;

import dfa_generation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Actually a stateful class
 */
public class Lexer {
    private NdfaGenerator ndfaGenerator;
    private DfaState dfa;
    private HashMap<String, Token> reservedWordMap;
    private String sourceCode;
    private int sourceIndex;
    private int curLine;
    private int curChar;

    public Lexer() throws IOException {
        ndfaGenerator = new NdfaGenerator();
        State ndfa = ndfaGenerator.generate();
        AutomataVisualizer.visualize(ndfa, "ndfa");

        dfa = NdfaToDfaConvertor.convert(ndfa);
        AutomataVisualizer.visualize(dfa, "dfa");

        reservedWordMap = new HashMap<String, Token>();
        for (Token token : Token.values()) {
            if (!token.isReservedWord())
                continue;

            reservedWordMap.put(token.getName(), token);
        }
    }

    public void loadSource(String source) {
        this.sourceCode = source;
        sourceIndex = 0;
        curLine = 1;
        curChar = 0;
    }

    public FoundToken nextToken() {
        // whitespace skip
        while (sourceIndex < sourceCode.length() && isWhiteSpace(sourceCode.charAt(sourceIndex)))
            nextChar();

        int foundTokenStartIndex = sourceIndex;
        int foundTokenStartChar = curChar;

        // inline comment detected
        if (nextTwoCharsAre('/', '/')) {
            nextChar();
            nextChar();

            while (sourceIndex < sourceCode.length() && sourceCode.charAt(sourceIndex) != '\n')
                nextChar();

            return new FoundToken(Token.IN_LINE_COMMENT,  sourceCode.substring(foundTokenStartIndex, sourceIndex), curLine, foundTokenStartChar);
        }

        // block comment detected
        if (nextTwoCharsAre('/', '*')) {
            int foundLine = curLine;
            int openers = 1;
            nextChar();
            nextChar();

            while (sourceIndex < sourceCode.length() && openers > 0) {
                if (sourceIndex < sourceCode.length() - 1) {
                    if(nextTwoCharsAre('/', '*')) {
                        openers++;
                        nextChar();
                        nextChar();
                    } else if (nextTwoCharsAre('*', '/')) {
                        openers--;
                        nextChar();
                        nextChar();
                    } else {
                        nextChar();
                    }
                } else {
                    nextChar();
                    return new FoundToken(Token.ERROR, cleanseStringOfNewlineChars(sourceCode.substring(foundTokenStartIndex, sourceIndex - 1)), foundLine, foundTokenStartChar);
                }
            }

            return new FoundToken(Token.BLOCK_COMMENT, cleanseStringOfNewlineChars(sourceCode.substring(foundTokenStartIndex, sourceIndex - 1)), foundLine, foundTokenStartChar);
        }

        if (sourceIndex >= sourceCode.length())
            return new FoundToken(Token.END_OF_FILE, sourceCode.substring(sourceIndex - 1, sourceIndex - 1), curLine, foundTokenStartChar);

        ArrayList<DfaState> currentGeneration = new ArrayList<DfaState>();
        currentGeneration.add(dfa);

        DfaState lastSeenTerminalState = null;
        int lastSeenTerminalStateSourceIndex = 0;

        while (currentGeneration.size() > 0 && sourceIndex < sourceCode.length()) {
            char next = peekChar();
            ArrayList<DfaState> offspring = new ArrayList<DfaState>();

            for (DfaState survivor : currentGeneration) {
                if (survivor.isTerminal()) {
                    lastSeenTerminalState = survivor;
                    lastSeenTerminalStateSourceIndex = sourceIndex;
                }

                for (State.Edge edge : survivor.getEdges()) {
                    if ((edge.label.equals(Token.LETTER.getName()) && isAlpha(next)) ||
                            (edge.label.equals(Token.DIGIT.getName()) && isDigit(next)) ||
                            (edge.label.equals(Token.NON_ZERO.getName()) && isNonZero(next)) ||
                            (edge.label.length() == 1 && edge.label.charAt(0) == next)) {
                        offspring.add((DfaState) edge.destination);
                    }
                }
            }

            currentGeneration = offspring;
            if (offspring.size() != 0)
                nextChar();
        }

        FoundToken ret;
        if (lastSeenTerminalState != null) {
            // todo: will totally fuck with the line and character count
            // backtracking
            sourceIndex = lastSeenTerminalStateSourceIndex;
            String lexeme = sourceCode.substring(foundTokenStartIndex, lastSeenTerminalStateSourceIndex);

            if (lastSeenTerminalState.getPathToken() == Token.IDENTIFIER && reservedWordMap.containsKey(lexeme))
                return new FoundToken(reservedWordMap.get(lexeme), lexeme, curLine, foundTokenStartChar);

            return new FoundToken(lastSeenTerminalState.getPathToken(), lexeme, curLine, foundTokenStartChar);
        } else if (foundTokenStartIndex == sourceIndex) {
            ret =  new FoundToken(Token.INVALID_CHAR, sourceCode.substring(sourceIndex, sourceIndex + 1), curLine, foundTokenStartChar);
            nextChar();
            return ret;
        } else {
            ret = new FoundToken(Token.ERROR, sourceCode.substring(foundTokenStartIndex, sourceIndex), curLine, foundTokenStartChar);
            nextChar();
            return ret;
        }
    }

    private char nextChar() {
        char next = peekChar();

        if (next == '\n') {
            curLine++;
            curChar = 0;
        } else {
            curChar++;
        }

        sourceIndex++;
        return next;
    }

    private char peekChar() {
        return sourceIndex < sourceCode.length() ? sourceCode.charAt(sourceIndex) : ' ';
    }

    private boolean nextTwoCharsAre(char c1, char c2) {
        return (sourceIndex < sourceCode.length() - 1 && sourceCode.charAt(sourceIndex) == c1 && sourceCode.charAt(sourceIndex+1) == c2);
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
        return (c == ' ' || c == '\n' || c == '\r' || c == '\t');
    }

    private static String cleanseStringOfNewlineChars(String s) {
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == '\n')
                sb.append('\\').append('n');
            else if (c == '\r')
                sb.append('\\').append('r');
            else
               sb.append(c);
        }

        return sb.toString();
    }
}
