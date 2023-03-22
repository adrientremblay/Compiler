package semantic_analysis;

public enum SymbolTableRowKind {
    FUNCTION("function"),
    CLASS("class"),
    VARIABLE("variable"),
    PARAMETER("param"),
    ATTRIBUTE("data"),
    INHERIT("inherit");

    private String name;

    SymbolTableRowKind(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
