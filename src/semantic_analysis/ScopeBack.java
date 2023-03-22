package semantic_analysis;

import syntactical_analysis.ast_generation.tree.SemanticConcept;

/**
 * Special Semantic Concept that doesn't appear in the AST
 * Only exists to be added to the stack to indicate that the scope should go back
 */
public class ScopeBack extends SemanticConcept {
    @Override
    public String getName() {
        return "ScopeBack (You should not see this hehe)";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitScopeBack(this);
    }
}
