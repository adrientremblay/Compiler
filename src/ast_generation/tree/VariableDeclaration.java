package ast_generation.tree;

public class VariableDeclaration extends SemanticConcept {
    public VariableDeclaration(SemanticConcept id, SemanticConcept type, SemanticConcept arrayOrObject) {
        addChild(id);
        addChild(type);
        addChild(arrayOrObject);
    }
    @Override
    public String getName() {
        return "VarDecl";
    }
}
