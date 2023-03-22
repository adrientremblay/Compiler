package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class FunctionDefinition extends SemanticConcept {
    private Identifier identifier;

    public FunctionDefinition(SemanticConcept statementBlock, SemanticConcept type, SemanticConcept parameterList, SemanticConcept scopeSpecification, SemanticConcept id) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
        addChild(statementBlock);
        addChild(scopeSpecification);

        this.identifier = (Identifier) id;
    }

    @Override
    public String getName() {
        return "FuncDef";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitFunctionDefinition(this);
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
