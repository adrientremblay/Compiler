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
