package ast_generation.tree;

import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;

public class Type extends SemanticConcept {
    @Override
    public String getName() {
        return "Type";
    }

    public Type(FoundToken member) {
        super();
        this.member = member;
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
