package ast_generation.tree;

public class AssignmentStatement extends SemanticConcept {
    public AssignmentStatement(SemanticConcept left, SemanticConcept right) {
        addChild(left);
        addChild(right);
    }

    @Override
    public String getName() {
        return null;
    }
}
