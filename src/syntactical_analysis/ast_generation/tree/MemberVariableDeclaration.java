package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;

public class MemberVariableDeclaration extends SemanticConcept {
    private Identifier identifier;
    private Type type;
    private Dimensions dimensions;

    public MemberVariableDeclaration(SemanticConcept arrayOrObject, SemanticConcept type, SemanticConcept id, SemanticConcept visibility) {
        addChild(id);
        addChild(type);
        addChild(arrayOrObject);
        addChild(visibility);

        this.identifier = (Identifier) id;
        this.type = (Type)  type;
        if (arrayOrObject instanceof Dimensions)
            this.dimensions = (Dimensions) arrayOrObject;
    }

    @Override
    public String getName() {
        return "VarDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(type.getMember().getLexeme());
        if (dimensions != null)
            sb.append(dimensions);

        return sb.toString();
    }
}
