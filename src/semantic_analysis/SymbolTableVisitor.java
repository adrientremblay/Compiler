package semantic_analysis;

import ast_generation.tree.Program;

public class SymbolTableVisitor {
    private SymbolTable globalSymbolTable;

    public SymbolTableVisitor() {

    }

    /**
     * Generates a new global table
     *
     * @param program
     */
    public void visitProgram(Program program) {
        globalSymbolTable = new SymbolTable("global");
    }

    public SymbolTable getGlobalSymbolTable() {
        return globalSymbolTable;
    }
}
