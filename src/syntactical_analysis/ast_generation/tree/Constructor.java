package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Constructor extends SemanticConcept {
    public Constructor(SemanticConcept params, SemanticConcept visibility) {
        addChild(params);
        addChild(visibility);
    }

    @Override
    public String getName() {
        return "Constructor";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
