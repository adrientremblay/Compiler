package ast_generation.tree.classes;

import ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class MemberList extends SemanticConcept {
    @Override
    public String getName() {
        return "MembList";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
