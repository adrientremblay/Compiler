package ast_generation.tree.statements;

import ast_generation.tree.SemanticConcept;

public class IfStatement extends SemanticConcept {
    public IfStatement(SemanticConcept block2, SemanticConcept block1, SemanticConcept relativeExpr)  {
        addChild(relativeExpr);
        addChild(block1);
        addChild(block2);
    }

    @Override
    public String getName() {
        return "IfStat";
    }
}
