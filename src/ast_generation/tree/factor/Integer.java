package ast_generation.tree.factor;

import ast_generation.tree.SemanticConcept;
import lexical_analysis.FoundToken;

public class Integer extends SemanticConcept {
    public Integer(FoundToken member) {
        this.member = member;
    }

    @Override
    public String getName() {
        return "Int";
    }
}
