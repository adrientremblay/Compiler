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
    EQUALS("eq", "= =", true, true, false),
    NOT_EQ("noteq", "< >", true, true, false),
    LESS_THAN("lt", "<", true, true, false),
    GREATER_THAN("gt", ">", true, true, false),
    LESS_THAN_OR_EQUAL("leq", "< =", true, true, false),
    GREATER_THAN_OR_EQUAL("geq", "> =", true, true, false),
    PLUS("plus", "+", true, true, false),
    MINUS("minus", "-", true, true, false),
    MULTIPLY("mult", "*", true, true, false),
    DIVIDE("div", "/", true, true, false),
    ASSIGN("assign", "=", true, true, false),
    OPEN_PAR("openpar", "(", true, true, false),
    CLOSE_PAR("closepar", ")", true, true, false),
    OPEN_CUBR("opencubr", "{", true, true, false),
    CLOSE_CUBR("closecubr", "}", true, true, false),
    OPEN_SQBR("opensqbr", "[", true, true, false),
    CLOSE_SQBR("closesqbr", "]", true, true, false),
    SEMI_COLON("semi", ";", true, true, false),
    COMMA("comma", ",", true, true, false),
    DOT("dot", ".", true, true, false),
    COLON("colon", ":", true, true, false),
    RETURN_TYPE("returntype", "= >", true, true, false),
    SCOPE_OP("scopeop", ": :", true, true, false),
    // RESERVED WORDS
    OR("or", "", false, false, true),
    AND("and", "", false, false, true),
    NOT("not", "", false, false, true),
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
