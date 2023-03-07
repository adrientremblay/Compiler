package ast_generation.tree;

import lexical_analysis.FoundToken;

public class RelativeOperator extends SemanticConcept{
    public RelativeOperator(FoundToken op) {
        member = op;
    }

    @Override
    public String getName() {
        return "RelOp";
    }
}
