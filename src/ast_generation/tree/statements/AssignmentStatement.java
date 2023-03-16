package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;

public class AssignmentStatement extends SemanticConcept {
    public AssignmentStatement(SemanticConcept left, SemanticConcept right) {
        addChild(left);
        addChild(right);
    }

    @Override
    public String getName() {
        return "AssigStat";
    }
}
