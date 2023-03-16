package ast_generation.tree;

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
