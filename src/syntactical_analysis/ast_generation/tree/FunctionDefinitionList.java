package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class FunctionDefinitionList extends SemanticConcept {
    @Override
    public String getName() {
        return "FuncDefList";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
