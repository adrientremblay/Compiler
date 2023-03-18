package ast_generation.tree.factor;

import ast_generation.tree.SemanticConcept;
import lexical_analysis.FoundToken;

public class Float extends SemanticConcept {
    public Float(FoundToken member) {
        this.member = member;
    }

    @Override
    public String getName() {
        return "Float";
    }
}
