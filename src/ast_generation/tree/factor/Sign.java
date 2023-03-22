package ast_generation.tree.factor;

import ast_generation.tree.SemanticConcept;
import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;

public class Sign extends SemanticConcept {
    public Sign(FoundToken sign) {
        this.member = sign;
    }

    @Override
    public String getName() {
        return "Sign";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
