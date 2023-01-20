package LexicalAnalysis;

public enum Token {
    IDENTIFIER();

    private String lexeme;
    private int foundOnLine;
    private int foundOnChar;

    Token() {
    }
}
