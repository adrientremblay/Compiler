package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class MultiplyOperation extends SemanticConcept {
    public MultiplyOperation(SemanticConcept leftHandSide) {
       addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "MultOp";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
