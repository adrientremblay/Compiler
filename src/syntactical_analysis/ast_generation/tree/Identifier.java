package syntactical_analysis.ast_generation.tree;

import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;

public class Identifier extends SemanticConcept {
    @Override
    public String getName() {
        return "Id";
    }

    public Identifier(FoundToken member) {
        super();
        this.member = member;
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
