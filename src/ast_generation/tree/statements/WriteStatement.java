package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;

public class WriteStatement extends SemanticConcept {
    public WriteStatement(SemanticConcept expression) {
        addChild(expression);
    }

    @Override
    public String getName() {
        return "WriteStat";
    }
}
