package ast_generation.tree;

public class SubtractionOperation extends SemanticConcept {
    public SubtractionOperation(SemanticConcept leftHandSide) {
        addChild(leftHandSide);
    }

    @Override
    public String getName() {
        return "SubOp";
    }
}
