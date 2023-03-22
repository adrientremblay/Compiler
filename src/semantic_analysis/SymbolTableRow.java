package semantic_analysis;

public class SymbolTableRow {
    private String name;
    private SymbolTableRowKind rowKind;
    private String type;
    private VisibilityKind visibilityKind;
    private SymbolTable symbolTableLink;

    public SymbolTableRow(String name, SymbolTableRowKind rowKind, String type) {
        this.name = name;
        this.rowKind = rowKind;
        this.type = type;
    }

    public SymbolTableRow(String name, SymbolTableRowKind rowKind, String type, VisibilityKind visibilityKind) {
        this.name = name;
        this.rowKind = rowKind;
        this.type = type;
        this.visibilityKind = visibilityKind;
    }

    public SymbolTableRow(String name, SymbolTableRowKind rowKind, String type, SymbolTable symbolTable) {
        this(name, rowKind, type);
        this.symbolTableLink = symbolTable;
    }

    public SymbolTableRow(String name, SymbolTableRowKind rowKind, String type, VisibilityKind visibilityKind, SymbolTable symbolTable) {
        this(name, rowKind, type, visibilityKind);
        this.symbolTableLink = symbolTable;
    }

    public String getName() {
        return name;
    }

    public SymbolTableRowKind getRowKind() {
        return rowKind;
    }

    public String getType() {
        return type;
    }

    public SymbolTable getSymbolTableLink() {
        return symbolTableLink;
    }

    public VisibilityKind getVisibilityKind() {
        return visibilityKind;
    }

    public void setSymbolTableLink(SymbolTable symbolTableLink) {
        this.symbolTableLink = symbolTableLink;
    }
}
