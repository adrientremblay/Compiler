package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Dimensions extends SemanticConcept {
    @Override
    public String getName() {
        return "Dims";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
