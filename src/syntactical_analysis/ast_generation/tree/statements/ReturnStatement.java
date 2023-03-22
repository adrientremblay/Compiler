package syntactical_analysis.ast_generation.tree.statements;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class ReturnStatement extends SemanticConcept {
    public ReturnStatement(SemanticConcept expression) {
        addChild(expression);
    }

    @Override
    public String getName() {
        return "ReturnStat";
    }


    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
