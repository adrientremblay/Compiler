package syntactical_analysis.ast_generation.tree.classes;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class FunctionDeclaration extends SemanticConcept {
    public FunctionDeclaration(SemanticConcept id, SemanticConcept parameterList, SemanticConcept type, SemanticConcept visibility) {
        addChild(type);
        addChild(parameterList);
        addChild(id);
        addChild(visibility);
    }

    @Override
    public String getName() {
        return "FuncDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
