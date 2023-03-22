package syntactical_analysis.ast_generation.tree.statements;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class ReadStatement extends SemanticConcept {
    public ReadStatement(SemanticConcept variable) {
        addChild(variable);
    }

    @Override
    public String getName() {
        return "ReadStat";
    }


    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
