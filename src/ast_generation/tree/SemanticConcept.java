package ast_generation.tree;

import lexical_analysis.FoundToken;
import semantic_analysis.SymbolTableVisitor;
import semantic_analysis.Visitable;

import java.util.HashSet;
import java.util.Set;

public abstract class SemanticConcept implements Visitable {
    private Set<SemanticConcept> children;
    private Set<SemanticConcept> neighbors;
    protected FoundToken member = null;

    public SemanticConcept() {
        children = new HashSet<SemanticConcept>();
        neighbors = new HashSet<SemanticConcept>();
    }

    public abstract String getName();

    public FoundToken getMember() {
        return member;
    }

    public Set<SemanticConcept> getChildren() {
        return children;
    }

    public Set<SemanticConcept> getNeighbors() {
        return neighbors;
    }

    public void addChild(SemanticConcept child) {
        children.add(child);
    }

    public abstract void accept(SymbolTableVisitor visitor);
}
