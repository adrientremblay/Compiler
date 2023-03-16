package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;

public class ReadStatement extends SemanticConcept {
    public ReadStatement(SemanticConcept variable) {
        addChild(variable);
    }

    @Override
    public String getName() {
        return "ReadStat";
    }
}
