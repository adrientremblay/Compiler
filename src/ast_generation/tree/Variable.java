package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Variable extends SemanticConcept{
    public Variable(SemanticConcept id, SemanticConcept indices) {
        addChild(id);
        addChild(indices);
    }

    @Override
    public String getName() {
        return "Var";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
