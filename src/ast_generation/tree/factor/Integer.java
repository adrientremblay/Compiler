package ast_generation.tree.factor;

import ast_generation.tree.SemanticConcept;
import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;

public class Integer extends SemanticConcept {
    public Integer(FoundToken member) {
        this.member = member;
    }

    @Override
    public String getName() {
        return "Int";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
