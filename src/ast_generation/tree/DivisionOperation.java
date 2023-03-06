package ast_generation.tree;

public class DivisionOperation extends SemanticConcept {
    public DivisionOperation(SemanticConcept leftHandSide) {
        addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "DivOp";
    }
}
