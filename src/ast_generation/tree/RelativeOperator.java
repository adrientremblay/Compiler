package ast_generation.tree;

import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;

public class RelativeOperator extends SemanticConcept{
    public RelativeOperator(FoundToken op) {
        member = op;
    }

    @Override
    public String getName() {
        return "RelOp";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
