package ast_generation.tree;

public class MultiplyOperation extends SemanticConcept {
    public MultiplyOperation(SemanticConcept leftHandSide) {
       addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "MultOp";
    }
}
