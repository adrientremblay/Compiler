package ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class ParameterList extends SemanticConcept{
    @Override
    public String getName() {
        return "ParamList";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
