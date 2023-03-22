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
        String functionName = functionDefinition.getIdentifier().getMember().getLexeme();
        String functionType = functionDefinition.getParameterList().toString() + ":" + functionDefinition.getType().getMember().getLexeme();

        currentScope.addRow(new SymbolTableRow(functionName, SymbolTableRowKind.FUNCTION, functionType));
    }

    public SymbolTable getGlobalSymbolTable() {
        return globalSymbolTable;
    }
}
