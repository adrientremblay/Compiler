package syntactical_analysis.ast_generation.tree.classes;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.ParameterList;
import syntactical_analysis.ast_generation.tree.ScopeSpecification;
import syntactical_analysis.ast_generation.tree.SemanticConcept;

public class Constructor extends SemanticConcept {
    private ParameterList parameterList;
    private Visibility visibility;
    private ScopeSpecification scopeSpecification;

    public Constructor(SemanticConcept params, SemanticConcept visibility, SemanticConcept scopeSpecification) {
        addChild(params);
        addChild(visibility);
        addChild(scopeSpecification);

        this.parameterList = (ParameterList) params;
        this.visibility = (Visibility) visibility;
        this.scopeSpecification = (ScopeSpecification) scopeSpecification;
    }

    @Override
    public String getName() {
        return "Constructor";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitConstructor(this);
    }

    public ParameterList getParameterList() {
        return parameterList;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public ScopeSpecification getScopeSpecification() {
        return scopeSpecification;
    }
}
