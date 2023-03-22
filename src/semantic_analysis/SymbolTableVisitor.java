package semantic_analysis;

import syntactical_analysis.ast_generation.tree.FunctionDefinition;
import syntactical_analysis.ast_generation.tree.Program;

public class SymbolTableVisitor {
    private SymbolTable globalSymbolTable;
    private SymbolTable currentScope;

    public SymbolTableVisitor() {

    }

    /**
     * Generates a new global table
     *
     * @param program
     */
    public void visitProgram(Program program) {
        currentScope = globalSymbolTable = new SymbolTable("global");
    }

    /**
     * Adds a function row to the current scope symbol table
     *
     * @param functionDefinition
     */
    public void visitFunctionDefinition(FunctionDefinition functionDefinition) {
        currentScope.addRow(new SymbolTableRow(functionDefinition.getIdentifier().getMember().getLexeme(), SymbolTableRowKind.FUNCTION, "balls")); //todo: finish this
    }

    public SymbolTable getGlobalSymbolTable() {
        return globalSymbolTable;
    }
}
