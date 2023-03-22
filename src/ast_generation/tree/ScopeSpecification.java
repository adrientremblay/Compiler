package ast_generation.tree;

import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;

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

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
