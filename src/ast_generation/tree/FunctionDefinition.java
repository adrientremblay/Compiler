package ast_generation.tree;

public class FunctionDefinition extends SemanticConcept {
    public FunctionDefinition(SemanticConcept id, SemanticConcept parameterList, SemanticConcept type, SemanticConcept statementBlock) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
        addChild(statementBlock);
    }

    @Override
    public String getName() {
        return "FuncDef";
    }
}