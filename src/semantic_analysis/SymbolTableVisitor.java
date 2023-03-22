package semantic_analysis;

import syntactical_analysis.ast_generation.tree.FunctionDefinition;
import syntactical_analysis.ast_generation.tree.Program;

public interface SymbolTableVisitor {
    void visitProgram(Program program);

    void visitFunctionDefinition(FunctionDefinition functionDefinition);

    void visitScopeBack(ScopeBack scopeBack);
}
