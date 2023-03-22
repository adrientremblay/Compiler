package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class Program extends SemanticConcept{
    public Program() {
    }

    @Override
    public String getName() {
        return "Prog";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
