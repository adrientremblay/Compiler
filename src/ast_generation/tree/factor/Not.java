package ast_generation.tree.factor;

import ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class Not extends SemanticConcept {
    public Not(SemanticConcept factor) {
        addChild(factor);
    }

    @Override
    public String getName() {
        return "Not";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
