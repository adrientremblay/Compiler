package syntactical_analysis.ast_generation.tree.function;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.SemanticConcept;

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
