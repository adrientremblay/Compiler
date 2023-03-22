package syntactical_analysis.ast_generation.tree.classes;

import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

public class ClassDeclaration extends SemanticConcept {
    public ClassDeclaration(SemanticConcept id, SemanticConcept inheritanceList, SemanticConcept memberList) {
        addChild(id);
        addChild(inheritanceList);
        addChild(memberList);
    }

    @Override
    public String getName() {
        return "ClassDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        // Do Nothing
    }
}
