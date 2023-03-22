package ast_generation.tree.classes;

import ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class FunctionDeclaration extends SemanticConcept {
    public FunctionDeclaration(SemanticConcept id, SemanticConcept parameterList, SemanticConcept type) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
    }

    @Override
    public String getName() {
        return "FuncDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
