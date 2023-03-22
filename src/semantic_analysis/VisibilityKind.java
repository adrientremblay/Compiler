package semantic_analysis;

import lexical_analysis.Token;
import syntactical_analysis.ast_generation.tree.classes.Visibility;

public enum VisibilityKind {
    PUBLIC("public"),
    PRIVATE("private");

    private String name;

    VisibilityKind(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static VisibilityKind kindOf(Visibility visibility) {
        Token token = visibility.getMember().getToken();

        if (token.equals(Token.PUBLIC))
            return PUBLIC;

        if (token.equals(Token.PRIVATE))
            return PRIVATE;

        return null;
    }
}
