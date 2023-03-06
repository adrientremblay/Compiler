package ast_generation.tree;

import lexical_analysis.FoundToken;

public class Type extends SemanticConcept {
    @Override
    public String getName() {
        return "Type";
    }

    public Type(FoundToken member) {
        super();
        this.member = member;
    }
}
