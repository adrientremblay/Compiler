package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class DivisionOperation extends SemanticConcept {
    public DivisionOperation(SemanticConcept leftHandSide) {
        addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "DivOp";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
