package ast_generation.tree.factor;

import ast_generation.tree.SemanticConcept;

public class Not extends SemanticConcept {
    public Not(SemanticConcept factor) {
        addChild(factor);
    }

    @Override
    public String getName() {
        return "Not";
    }
}
