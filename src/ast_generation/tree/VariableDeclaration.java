package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class VariableDeclaration extends SemanticConcept {
    public VariableDeclaration(SemanticConcept id, SemanticConcept type, SemanticConcept arrayOrObject) {
        addChild(id);
        addChild(type);
        addChild(arrayOrObject);
    }
    @Override
    public String getName() {
        return "VarDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
