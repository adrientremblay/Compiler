package semantic_analysis;

public interface Visitable {
    void accept(SymbolTableVisitor visitor);
}
