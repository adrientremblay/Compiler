package ast_generation.tree;

import lexical_analysis.FoundToken;

public class ScopeSpecification extends SemanticConcept{
    public ScopeSpecification() {

    }

    public ScopeSpecification(FoundToken spec) {
        this.member = spec;
    }

    @Override
    public String getName() {
        return "ScopeSpec";
    }
}
