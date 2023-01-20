package LexicalAnalysis;

public enum Token {
    IDENTIFIER("id", "letter alphanum*", true, true),
    ALPHA_NUMERIC("alphanum", "letter | digit | _", false, true),
    INTEGER("integer", "[nonzero digit*] | 0", true, true),
    FLOAT("float", "integer fraction [e [+ | -] integer]", true, true),
    FRACTION("fraction", ". [digit* nonzero] | 0", false, true),
    LETTER("letter", "a..z | A..Z", false, false),
    DIGIT("digit", "0..9", false, false),
    NON_ZERO("nonzero", "1..9", false, false),
    EQUALS_EQUALS("", "= =", true, false),
    DIAMOND("", "< >", true, false),
    LESS_THAN("", "<", true, false),
    GREATER_THAN("", ">", true, false),
    LESS_THAN_OR_EQUAL("", "< =", true, false),
    GREATER_THAN_OR_EQUAL("", "> =", true, false),
    PLUS("", "+", true, false),
    MINUS("", "-", true, false),
    STAR("", "*", true, false),
    FORWARD_SLASH("", "/", true, false),
    EQUALS("", "=", true, false),
    LEFT_BRACKET("", "(", true, false),
    RIGHT_BRACKET("", ")", true, false),
    LEFT_BRACE("", "{", true, false),
    RIGHT_BRACE("", "}", true, false),
    LEFT_SQUARE_BRACKET("", "[", true, false),
    RIGHT_SQUARE_BRACKET("", "]", true, false),
    SEMI_COLON("", ";", true, false),
    COMMA("", ",", true, false),
    PERIOD("", ".", true, false),
    COLON("", ":", true, false),
    ARROW("", "=>", true, false),
    COLON_COLON("", "::", true, false),

    ;

    private final String name; // name as found in regular expression
    private final String regex; // the regular expression
    private final boolean standalone; // todo: find better name
    private final boolean expand;


    Token(String name, String regex, boolean standalone, boolean expand) {
        this.name = name;
        this.regex = regex;
        this.standalone = standalone;
        this.expand = expand;
    }
}
