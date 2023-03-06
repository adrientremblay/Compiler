package ast_generation.tree;

public class VariableDeclaration extends SemanticConcept {
    public VariableDeclaration(SemanticConcept id, SemanticConcept type) {
        addChild(id);
        addChild(type);
    }
    @Override
    public String getName() {
        return "VarDecl";
    }
}
