package ast_generation.tree;

public class Dot extends SemanticConcept{
    public Dot(SemanticConcept dotParam1, SemanticConcept dotParam2) {
        addChild(dotParam1);
        addChild(dotParam2);
    }

    @Override
    public String getName() {
        return "Dot";
    }
}
