package ast_generation.tree;

public class FunctionDefinition extends SemanticConcept {
    public FunctionDefinition(SemanticConcept id, SemanticConcept parameterList, SemanticConcept type) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
    }

    @Override
    public String getName() {
        return "FuncDef";
    }
}
