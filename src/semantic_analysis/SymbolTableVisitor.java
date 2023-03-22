package semantic_analysis;

import syntactical_analysis.ast_generation.tree.classes.Constructor;
import syntactical_analysis.ast_generation.tree.FunctionDefinition;
import syntactical_analysis.ast_generation.tree.classes.ClassDeclaration;
import syntactical_analysis.ast_generation.tree.classes.FunctionDeclaration;
import syntactical_analysis.ast_generation.tree.statements.LocalVariableDeclaration;

public interface SymbolTableVisitor {
    void visitConstructor(Constructor constructor);
    void visitFunctionDeclaration(FunctionDeclaration functionDeclaration);
    void visitFunctionDefinition(FunctionDefinition functionDefinition);

    void visitScopeBack(ScopeBack scopeBack);

    void visitLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration);

    void visitClassDeclaration(ClassDeclaration classDeclaration);
}
