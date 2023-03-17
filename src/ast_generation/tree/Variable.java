package ast_generation.tree;

public class Variable extends SemanticConcept{
    public Variable(SemanticConcept id) {
        addChild(id);
    }

    @Override
    public String getName() {
        return "Var";
    }
}
