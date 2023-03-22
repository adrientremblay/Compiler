package syntactical_analysis.ast_generation.tree.classes;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.*;

public class MemberVariableDeclaration extends SemanticConcept {
    private Identifier identifier;
    private Type type;
    private Dimensions dimensions;
    private ScopeSpecification scopeSpecification;

    public MemberVariableDeclaration(SemanticConcept arrayOrObject, SemanticConcept type, SemanticConcept id, SemanticConcept visibility, ScopeSpecification scopeSpecification) {
        addChild(id);
        addChild(type);
        addChild(arrayOrObject);
        addChild(visibility);
        addChild(scopeSpecification);

        this.identifier = (Identifier) id;
        this.type = (Type)  type;
        this.scopeSpecification = scopeSpecification;
        if (arrayOrObject instanceof Dimensions)
            this.dimensions = (Dimensions) arrayOrObject;
    }

    @Override
    public String getName() {
        return "MemberVarDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitMemberVariableDeclaration(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(type.getMember().getLexeme());
        if (dimensions != null)
            sb.append(dimensions);

        return sb.toString();
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public ScopeSpecification getScopeSpecification() {
        return scopeSpecification;
    }
}
