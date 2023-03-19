package ast_generation.tree;

public class Constructor extends SemanticConcept {
    public Constructor(SemanticConcept params) {
        addChild(params);
    }

    @Override
    public String getName() {
        return "Constructor";
    }
}
