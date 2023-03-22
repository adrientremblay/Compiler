package semantic_analysis;

public class SymbolTableRow {
    private String name;
    private SymbolTableRowKind kind;
    private String type;
    private SymbolTable symbolTableLink;

    public SymbolTableRow(String name, SymbolTableRowKind kind, String type) {
        this.name = name;
        this.kind = kind;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public SymbolTableRowKind getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public SymbolTable getSymbolTableLink() {
        return symbolTableLink;
    }
}
