package ast_generation.tree;

import lexical_analysis.FoundToken;

public class Identifier extends SemanticConcept {
    @Override
    public String getName() {
        return "Id";
    }

    public Identifier(FoundToken member) {
        super();
        this.member = member;
    }
}
