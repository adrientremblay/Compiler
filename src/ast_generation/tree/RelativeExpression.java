package ast_generation.tree;

public class RelativeExpression extends SemanticConcept{

    public RelativeExpression(SemanticConcept arithExpr1, SemanticConcept relOp, SemanticConcept arithExpr2) {
        addChild(arithExpr1);
        addChild(relOp);
        addChild(arithExpr2);
    }

    @Override
    public String getName() {
        return "RelExpr";
    }
}
