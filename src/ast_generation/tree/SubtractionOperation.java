package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class SubtractionOperation extends SemanticConcept {
    public SubtractionOperation(SemanticConcept leftHandSide) {
        addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "SubOp";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
