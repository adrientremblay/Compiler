package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Nothing extends SemanticConcept {
    @Override
    public String getName() {
        return "Nothing (not supposed to appear)";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
