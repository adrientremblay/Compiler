package syntactical_analysis.ast_generation.tree.classes;

import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.SemanticConcept;

public class Visibility extends SemanticConcept {
    public Visibility(FoundToken foundToken) {
        this.member = foundToken;
    }

    @Override
    public String getName() {
        return "Visibility";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
