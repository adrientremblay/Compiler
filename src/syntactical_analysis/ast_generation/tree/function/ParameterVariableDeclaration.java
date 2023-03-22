package syntactical_analysis.ast_generation.tree.function;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.Dimensions;
import syntactical_analysis.ast_generation.tree.Identifier;
import syntactical_analysis.ast_generation.tree.SemanticConcept;
import syntactical_analysis.ast_generation.tree.Type;

public class ParameterVariableDeclaration extends SemanticConcept {
    private Identifier identifier;
    private Type type;
    private Dimensions dimensions;

    public ParameterVariableDeclaration(SemanticConcept arrayOrObject, SemanticConcept type, SemanticConcept id) {
        addChild(id);
        addChild(type);
        addChild(arrayOrObject);

        this.identifier = (Identifier) id;
        this.type = (Type)  type;
        if (arrayOrObject instanceof Dimensions)
            this.dimensions = (Dimensions) arrayOrObject;
    }
    @Override
    public String getName() {
        return "ParamVarDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitParameterVariableDeclaration(this);
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
}
