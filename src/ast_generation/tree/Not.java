package ast_generation.tree;

public class Not extends SemanticConcept {
    public Not(SemanticConcept factor) {
        addChild(factor);
    }

    @Override
    public String getName() {
        return "Not";
    }
}
