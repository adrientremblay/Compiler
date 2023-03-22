package syntactical_analysis.ast_generation.tree.statements;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class WriteStatement extends SemanticConcept {
    public WriteStatement(SemanticConcept expression) {
        addChild(expression);
    }

    @Override
    public String getName() {
        return "WriteStat";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
