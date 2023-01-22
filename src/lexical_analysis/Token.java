package lexical_analysis;

public enum Token {
    // CORE TOKENS
    IDENTIFIER("id", "letter alphanum*", true, true, false),
    ALPHA_NUMERIC("alphanum", "letter | digit | _", false, true, false),
    INT_NUM("intnum", "[nonzero digit*] | 0", true, true, false),
    FLOAT_NUM("floatnum", "intnum fraction [e [+ | -] intnum]", true, true, false),
    FRACTION("fraction", ". [digit* nonzero] | 0", false, true, false),
    LETTER("letter", "a..z | A..Z", false, false, false),
    DIGIT("digit", "0..9", false, false, false),
    NON_ZERO("nonzero", "1..9", false, false, false),
    // OPERATORS AND PUNCTUATION
    EQUALS_EQUALS("eq", "= =", true, true, false),
    DIAMOND("diamond", "< >", true, true, false),
    LESS_THAN("lessthan", "<", true, true, false),
    GREATER_THAN("greaterthan", ">", true, true, false),
    LESS_THAN_OR_EQUAL("lessthanorequal", "< =", true, true, false),
    GREATER_THAN_OR_EQUAL("greaterthanorequal", "> =", true, true, false),
    PLUS("plus", "+", true, true, false),
    MINUS("minus", "-", true, true, false),
    STAR("star", "*", true, true, false),
    FORWARD_SLASH("forwardslash", "/", true, true, false),
    EQUALS("equals", "=", true, true, false),
    LEFT_BRACKET("openpar", "(", true, true, false),
    RIGHT_BRACKET("openpar", ")", true, true, false),
    LEFT_BRACE("leftbrace", "{", true, true, false),
    RIGHT_BRACE("rightbrace", "}", true, true, false),
    LEFT_SQUARE_BRACKET("leftsquarebracket", "[", true, true, false),
    RIGHT_SQUARE_BRACKET("rightsquarebracket", "]", true, true, false),
    SEMI_COLON("semi", ";", true, true, false),
    COMMA("comma", ",", true, true, false),
    PERIOD("period", ".", true, true, false),
    COLON("colon", ":", true, true, false),
    ARROW("arrow", "= >", true, true, false),
    COLON_COLON("coloncolon", ": :", true, true, false),
    // RESERVED WORDS
    INTEGER("integer", "", false, false, true),
    FLOAT("float", "", false, false, true),
    VOID("void", "", false, false, true),
    CLASS("class", "", false, false, true),
    SELF("self", "", false, false, true),
    ISA("isa", "", false, false, true),
    WHILE("while", "", false, false, true),
    IF("if", "", false, false, true),
    THEN("then", "", false, false, true),
    ELSE("else", "", false, false, true),
    READ("read", "", false, false, true),
    WRITE("write", "", false, false, true),
    RETURN("return", "", false, false, true),
    LOCAL_VAR("localvar", "", false, false, true),
    CONSTRUCTOR("constructor", "", false, false, true),
    ATTRIBUTE("attribute", "", false, false, true),
    FUNCTION("function", "", false, false, true),
    PUBLIC("public", "", false, false, true),
    PRIVATE("private", "", false, false, true),
    // HELPER TOKENS
    ERROR("error", "", false, false, false),
    END_OF_FILE("endoffile", "", false, false, false);

    private final String name; // name as found in regular expression
    private final String regex; // the regular expression
    private final boolean standalone; // todo: find better name
    private final boolean expand;
    private final boolean reservedWord;

    Token(String name, String regex, boolean standalone, boolean expand, boolean reservedWord) {
        this.name = name;
        this.regex = regex;
        this.standalone = standalone;
        this.expand = expand;
        this.reservedWord = reservedWord;
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

    public boolean isReservedWord() {
        return reservedWord;
    }
}
