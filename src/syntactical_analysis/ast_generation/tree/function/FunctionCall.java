package syntactical_analysis.ast_generation.tree.function;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.SemanticConcept;

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
