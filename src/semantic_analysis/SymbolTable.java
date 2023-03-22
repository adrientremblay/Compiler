package semantic_analysis;

import java.util.HashSet;

public class SymbolTable {
    private String name;
    private HashSet<SymbolTableRow> rows;

    public SymbolTable(String name) {
        this.name = name;
        rows = new HashSet<SymbolTableRow>();
    }

    public String getName() {
        return name;
    }

    public void addRow(SymbolTableRow row) {
        this.rows.add(row);
    }

    public HashSet<SymbolTableRow> getRows() {
        return rows;
    }
}
