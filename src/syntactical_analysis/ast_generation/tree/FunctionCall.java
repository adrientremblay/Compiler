package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class FunctionCall extends SemanticConcept {
    public FunctionCall(SemanticConcept paramz, SemanticConcept funcNaem) {
        addChild(paramz);
        addChild(funcNaem);
    }

    @Override
    public String getName() {
        return "FunctionCall";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
