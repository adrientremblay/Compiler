package lexical_analysis;

public enum Token {
    // CORE TOKENS
    IDENTIFIER("id", "letter alphanum*", true, true),
    ALPHA_NUMERIC("alphanum", "letter | digit | _", false, true),
    INT_NUM("intnum", "[nonzero digit*] | 0", true, true),
    FLOAT_NUM("floatnum", "intnum fraction [e [+ | -] intnum]", true, true),
    FRACTION("fraction", ". [digit* nonzero] | 0", false, true),
    LETTER("letter", "a..z | A..Z", false, false),
    DIGIT("digit", "0..9", false, false),
    NON_ZERO("nonzero", "1..9", false, false),
    // OPERATORS AND PUNCTUATION
    EQUALS_EQUALS("eq", "= =", true, true),
    DIAMOND("diamond", "< >", true, true),
    LESS_THAN("lessthan", "<", true, true),
    GREATER_THAN("greaterthan", ">", true, true),
    LESS_THAN_OR_EQUAL("lessthanorequal", "< =", true, true),
    GREATER_THAN_OR_EQUAL("greaterthanorequal", "> =", true, true),
    PLUS("plus", "+", true, true),
    MINUS("minus", "-", true, true),
    STAR("star", "*", true, true),
    FORWARD_SLASH("forwardslash", "/", true, true),
    EQUALS("equals", "=", true, true),
    LEFT_BRACKET("openpar", "(", true, true),
    RIGHT_BRACKET("openpar", ")", true, true),
    LEFT_BRACE("leftbrace", "{", true, true),
    RIGHT_BRACE("rightbrace", "}", true, true),
    LEFT_SQUARE_BRACKET("leftsquarebracket", "[", true, true),
    RIGHT_SQUARE_BRACKET("rightsquarebracket", "]", true, true),
    SEMI_COLON("semi", ";", true, true),
    COMMA("comma", ",", true, true),
    PERIOD("period", ".", true, true),
    COLON("colon", ":", true, true),
    ARROW("arrow", "= >", true, true),
    COLON_COLON("coloncolon", ": :", true, true),
    // RESERVED WORDS
    INTEGER("integer", "", false, false),
    FLOAT("float", "", false, false),
    VOID("void", "", false, false),
    CLASS("class", "", false, false),
    SELF("self", "", false, false),
    ISA("isa", "", false, false),
    WHILE("while", "", false, false),
    IF("if", "", false, false),
    THEN("then", "", false, false),
    ELSE("else", "", false, false),
    READ("read", "", false, false),
    WRITE("write", "", false, false),
    RETURN("return", "", false, false),
    LOCAL_VAR("localvar", "", false, false),
    CONSTRUCTOR("constructor", "", false, false),
    ATTRIBUTE("attribute", "", false, false),
    FUNCTION("function", "", false, false),
    PUBLIC("public", "", false, false),
    PRIVATE("private", "", false, false),
    // HELPER TOKENS
    ERROR("error", "", false, false),
    END_OF_FILE("endoffile", "", false, false);

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
