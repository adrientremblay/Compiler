package lexical_analysis;

public class FoundToken {
    private Token token;
    private String lexeme;
    private int foundOnLine;
    private int foundOnChar;

    public FoundToken(Token token, String lexeme, int foundOnLine, int foundOnChar) {
        this.token = token;
        this.lexeme = lexeme;
        this.foundOnLine = foundOnLine;
        this.foundOnChar = foundOnChar;
    }

    @Override
    public String toString() {
        return "[" + token.getName() + ", " + lexeme + ", " + String.valueOf(foundOnLine) + ":" + String.valueOf(foundOnChar) + "]";
    }

    public Token getToken() {
        return token;
    }

    public int getFoundOnLine() {
        return foundOnLine;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getFoundOnChar() {
        return foundOnChar;
    }
}
