package syntactical_analysis.ast_generation.tree.classes;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class InheritanceList extends SemanticConcept {
    @Override
    public String getName() {
        return "InherList";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
