package ast_generation.tree;

public class FunctionCall extends SemanticConcept {
    public FunctionCall(SemanticConcept paramz, SemanticConcept funcNaem) {
        addChild(paramz);
        addChild(funcNaem);
    }

    @Override
    public String getName() {
        return "FunctionCall";
    }
}
