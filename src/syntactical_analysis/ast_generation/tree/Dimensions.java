package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.factor.Integer;
import syntactical_analysis.ast_generation.tree.factor.Not;

public class Dimensions extends SemanticConcept {
    @Override
    public String getName() {
        return "Dims";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (SemanticConcept child : getChildren()){
            if (child instanceof Nothing)
                continue;

            Integer integer = (Integer) child;
            sb.append("[");
            sb.append(integer.member.getLexeme());
            sb.append("]");
        }

        if (getChildren().size() == 0) {
            sb.append("[");
            sb.append("]");
        }

        return sb.toString();
    }
}
