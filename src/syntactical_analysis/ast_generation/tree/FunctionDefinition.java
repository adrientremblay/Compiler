package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class FunctionDefinition extends SemanticConcept {
    public FunctionDefinition(SemanticConcept id, SemanticConcept scopeSpecification, SemanticConcept parameterList, SemanticConcept type, SemanticConcept statementBlock) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
        addChild(statementBlock);
        addChild(scopeSpecification);
    }

    @Override
    public String getName() {
        return "FuncDef";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
