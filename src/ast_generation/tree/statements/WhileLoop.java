package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class WhileLoop extends SemanticConcept {
    public WhileLoop(SemanticConcept statBlock, SemanticConcept relExpr) {
        addChild(statBlock);
        addChild(relExpr);
    }

    @Override
    public String getName() {
        return "WhileLoop";
    }


    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
