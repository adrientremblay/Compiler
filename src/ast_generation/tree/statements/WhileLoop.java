package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;

public class WhileLoop extends SemanticConcept {
    public WhileLoop(SemanticConcept statBlock, SemanticConcept relExpr) {
        addChild(statBlock);
        addChild(relExpr);
    }

    @Override
    public String getName() {
        return "WhileLoop";
    }
}
