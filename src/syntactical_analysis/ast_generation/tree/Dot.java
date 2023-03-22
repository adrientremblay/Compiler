package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Dot extends SemanticConcept{
    public Dot(SemanticConcept dotParam1, SemanticConcept dotParam2) {
        addChild(dotParam1);
        addChild(dotParam2);
    }

    @Override
    public String getName() {
        return "Dot";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
