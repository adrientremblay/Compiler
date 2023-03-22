package syntactical_analysis.ast_generation.tree.classes;

import syntactical_analysis.ast_generation.tree.Identifier;
import syntactical_analysis.ast_generation.tree.SemanticConcept;
import semantic_analysis.SymbolTableVisitor;

import java.util.IdentityHashMap;

public class ClassDeclaration extends SemanticConcept {
    private MemberList memberList;
    private InheritanceList inheritanceList;
    private Identifier identifier;

    public ClassDeclaration(SemanticConcept memberList, SemanticConcept inheritanceList, SemanticConcept id) {
        addChild(id);
        addChild(inheritanceList);
        addChild(memberList);

        this.memberList = (MemberList) memberList;
        this.inheritanceList = (InheritanceList) inheritanceList;
        this.identifier = (Identifier) id;
    }

    @Override
    public String getName() {
        return "ClassDecl";
    }

    @Override
    public void accept(SymbolTableVisitor visitor) {
        visitor.visitClassDeclaration(this);
    }

    public MemberList getMemberList() {
        return memberList;
    }

    public InheritanceList getInheritanceList() {
        return inheritanceList;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
