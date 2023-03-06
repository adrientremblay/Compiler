package ast_generation.tree.classes;

import ast_generation.tree.SemanticConcept;

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
}
