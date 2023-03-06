package ast_generation.tree;

import lexical_analysis.FoundToken;

public class Num extends SemanticConcept {
    public Num(FoundToken member) {
        super();
        this.member = member;
    }

    @Override
    public String getName() {
        return "Num";
    }
}