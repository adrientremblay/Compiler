package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Indices extends SemanticConcept {
    @Override
    public String getName() {
        return "Indices";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
