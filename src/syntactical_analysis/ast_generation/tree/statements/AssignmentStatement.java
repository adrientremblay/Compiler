package syntactical_analysis.ast_generation.tree.statements;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class AssignmentStatement extends SemanticConcept {
    public AssignmentStatement(SemanticConcept left, SemanticConcept right) {
        addChild(left);
        addChild(right);
    }

    @Override
    public String getName() {
        return "AssigStat";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
