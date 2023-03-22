package syntactical_analysis.ast_generation.tree.classes;

import syntactical_analysis.ast_generation.tree.*;
import semantic_analysis.SymbolTableVisitor;

public class FunctionDeclaration extends SemanticConcept {
    private Type type;
    private ParameterList parameterList;
    private Identifier identifier;
    private Visibility visibility;
    private ScopeSpecification scopeSpecification;

    public FunctionDeclaration(SemanticConcept type, SemanticConcept parameterList, SemanticConcept id, SemanticConcept visibility, ScopeSpecification scopeSpecification) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
        addChild(visibility);
        addChild(scopeSpecification);

        this.type = (Type) type;
        this.parameterList = (ParameterList) parameterList;
        this.identifier = (Identifier) id;
        this.visibility = (Visibility) visibility;
        this.scopeSpecification = scopeSpecification;
    }

    @Override
    public String getName() {
        return "FuncDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitFunctionDeclaration(this);
    }

    public Type getType() {
        return type;
    }

    public ParameterList getParameterList() {
        return parameterList;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public ScopeSpecification getScopeSpecification() {
        return scopeSpecification;
    }
}
