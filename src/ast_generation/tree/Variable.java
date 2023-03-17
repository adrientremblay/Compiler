package ast_generation.tree;

public class Variable extends SemanticConcept{
    public Variable(SemanticConcept id, SemanticConcept indices) {
        addChild(id);
        addChild(indices);
    }

    @Override
    public String getName() {
        return "Var";
    }
}
