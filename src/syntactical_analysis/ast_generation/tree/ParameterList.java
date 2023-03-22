package syntactical_analysis.ast_generation.tree;

import semantic_analysis.SymbolTableVisitor;
import syntactical_analysis.ast_generation.tree.function.ParameterVariableDeclaration;

public class ParameterList extends SemanticConcept{
    @Override
    public String getName() {
        return "ParamList";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        int childNum = 1;
        for (SemanticConcept child : getChildren()) {
            if (child instanceof VariableDeclaration) {
                VariableDeclaration variableDeclaration = (VariableDeclaration) child;
                sb.append(variableDeclaration);
            } else {
                ParameterVariableDeclaration variableDeclaration = (ParameterVariableDeclaration) child;
                sb.append(variableDeclaration);
            }
            if (childNum < getChildren().size())
                sb.append(',');
            childNum++;
        }
        sb.append(")");

        return sb.toString();
    }
}
