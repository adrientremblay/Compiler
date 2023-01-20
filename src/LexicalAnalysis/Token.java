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
    EQUALS_EQUALS("equalsequals", "= =", true, true),
    DIAMOND("diamond", "< >", true, true),
    LESS_THAN("lessthan", "<", true, true),
    GREATER_THAN("greaterthan", ">", true, true),
    LESS_THAN_OR_EQUAL("lessthanorequal", "< =", true, true),
    GREATER_THAN_OR_EQUAL("greaterthanorequal", "> =", true, true),
    PLUS("plus", "+", true, true),
    MINUS("minus", "-", true, true),
    // todo: implement * and [ ]
//    STAR("star", "*", true, true),
    FORWARD_SLASH("forwardslash", "/", true, true),
    EQUALS("equals", "=", true, true),
    LEFT_BRACKET("leftbracket", "(", true, true),
    RIGHT_BRACKET("rightbracket", ")", true, true),
    LEFT_BRACE("leftbrace", "{", true, true),
    RIGHT_BRACE("rightbrace", "}", true, true),
//    LEFT_SQUARE_BRACKET("leftsquarebracket", "[", true, true),
//    RIGHT_SQUARE_BRACKET("rightsquarebracket", "]", true, true),
    SEMI_COLON("semicolon", ";", true, true),
    COMMA("comma", ",", true, true),
    PERIOD("period", ".", true, true),
    COLON("colon", ":", true, true),
    ARROW("arrow", "= >", true, true),
    COLON_COLON("coloncolon", ": :", true, true);

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

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public boolean isExpand() {
        return expand;
    }
}
