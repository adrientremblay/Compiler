package ast_generation.tree;

public class AdditionOperation extends SemanticConcept {
    public AdditionOperation(SemanticConcept leftHandSide) {
        addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "AddOp";
    }
}
