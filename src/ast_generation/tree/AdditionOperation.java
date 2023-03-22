package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class AdditionOperation extends SemanticConcept {
    public AdditionOperation(SemanticConcept leftHandSide) {
        addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "AddOp";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
