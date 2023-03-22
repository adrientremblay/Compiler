package syntactical_analysis.ast_generation.tree.statements;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.Dimensions;
import syntactical_analysis.ast_generation.tree.Identifier;
import syntactical_analysis.ast_generation.tree.SemanticConcept;
import syntactical_analysis.ast_generation.tree.Type;

public class LocalVariableDeclaration extends SemanticConcept {
    private Identifier identifier;
    private Type type;
    private Dimensions dimensions;

    public LocalVariableDeclaration(SemanticConcept arrayOrObject, SemanticConcept type, SemanticConcept id) {
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
        return "LocalVarDecl";
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
