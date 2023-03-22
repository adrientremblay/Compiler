package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class FunctionDefinition extends SemanticConcept {
    private Identifier identifier;
    private Type type;
    private ParameterList parameterList;
    private ScopeSpecification scopeSpecification;

    public FunctionDefinition(SemanticConcept statementBlock, SemanticConcept type, SemanticConcept parameterList, SemanticConcept scopeSpecification, SemanticConcept id) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
        addChild(statementBlock);
        addChild(scopeSpecification);

        this.type = (Type) type;
        this.parameterList = (ParameterList) parameterList;

        if (id instanceof Identifier) {
            this.identifier = (Identifier) id;
            this.scopeSpecification = (ScopeSpecification) scopeSpecification;
        } else {
            this.identifier = (Identifier) scopeSpecification;
            this.scopeSpecification = (ScopeSpecification) id;
        }
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

    public Type getType() {
        return type;
    }

    public ParameterList getParameterList() {
        return parameterList;
    }

    public ScopeSpecification getScopeSpecification() {
        return scopeSpecification;
    }
}
