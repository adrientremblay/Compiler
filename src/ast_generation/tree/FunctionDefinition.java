package ast_generation.tree;

public class FunctionDefinition extends SemanticConcept {
    public FunctionDefinition(SemanticConcept type, SemanticConcept id) {
        addChild(type);
        addChild(id);
    }

    @Override
    public String getName() {
        return "FuncDef";
    }
}
