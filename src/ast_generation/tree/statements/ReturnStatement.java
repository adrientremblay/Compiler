package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;

public class ReturnStatement extends SemanticConcept {
    public ReturnStatement(SemanticConcept expression) {
        addChild(expression);
    }

    @Override
    public String getName() {
        return "ReturnStat";
    }
}
