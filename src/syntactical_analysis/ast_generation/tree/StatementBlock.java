package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class StatementBlock extends SemanticConcept{
    @Override
    public String getName() {
        return "StatBlock";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
