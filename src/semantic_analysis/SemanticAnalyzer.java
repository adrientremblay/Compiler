package semantic_analysis;

import syntactical_analysis.ast_generation.tree.*;
import syntactical_analysis.Parser;
import syntactical_analysis.ast_generation.tree.classes.ClassDeclaration;
import syntactical_analysis.ast_generation.tree.classes.ClassDeclarationList;
import syntactical_analysis.ast_generation.tree.statements.LocalVariableDeclaration;

import java.util.HashMap;
import java.util.Stack;

public class SemanticAnalyzer implements SymbolTableVisitor {
    private Parser parser;

    private SymbolTablePrinter symbolTablePrinter;
    private SymbolTable globalSymbolTable;
    private Stack<SymbolTable> scopeStack;
    private Stack<SemanticConcept> astTraversalStack;
    private HashMap<String, SymbolTable> classMap;

    public SemanticAnalyzer() {
        parser = new Parser();
    }

    public void loadSource(String sourceFilePath) {
        parser.loadSource(sourceFilePath);
        symbolTablePrinter = new SymbolTablePrinter(sourceFilePath);
    }

    public SymbolTable generateSymbolTable() {
        Program ast = parser.parse();

        astTraversalStack = new Stack<>();
        scopeStack = new Stack<SymbolTable>();
        globalSymbolTable = new SymbolTable("global");
        scopeStack.add(globalSymbolTable);
        classMap = new HashMap<String, SymbolTable>();

        // We want to forcefully visit the functions then the classes instead of pushing the ast node directly
        FunctionDefinitionList functionDefinitionList = null;
        ClassDeclarationList classDeclarationList = null;
        for (SemanticConcept child : ast.getChildren())
            if (child instanceof  FunctionDefinitionList)
                functionDefinitionList = (FunctionDefinitionList) child;
            else
                classDeclarationList = (ClassDeclarationList) child;
        astTraversalStack.push(functionDefinitionList);
        astTraversalStack.push(classDeclarationList);

        while (!astTraversalStack.isEmpty()) {
            SemanticConcept curNode = astTraversalStack.pop();
            curNode.accept(this);
            astTraversalStack.addAll(curNode.getChildren());
        }

        symbolTablePrinter.writeGlobalSymbolTable(globalSymbolTable);

        return globalSymbolTable;
    }

    /**
     * Adds a function row to the current scope symbol table
     *
     * @param functionDefinition
     */
    @Override
    public void visitFunctionDefinition(FunctionDefinition functionDefinition) {
        String functionName = functionDefinition.getIdentifier().getMember().getLexeme();

        String functionType = functionDefinition.getParameterList().toString() + ":";
        functionType += (functionDefinition.getType().getMember() != null) ? functionDefinition.getType().getMember().getLexeme() :
            functionDefinition.getScopeSpecification().getMember().getLexeme(); // constructor

        String functionTableName = functionDefinition.getScopeSpecification().getMember() == null ?
                functionName :
                functionDefinition.getScopeSpecification().getMember().getLexeme() + "::" + functionName;

        SymbolTable functionBodySymbolTable = new SymbolTable(functionTableName);

        SymbolTableRow functionRow = new SymbolTableRow(functionName, SymbolTableRowKind.FUNCTION, functionType, functionBodySymbolTable);

        if (functionDefinition.getScopeSpecification().getMember() == null) {
            scopeStack.peek().addRow(functionRow);
        } else if (classMap.containsKey(functionDefinition.getScopeSpecification().getMember().getLexeme())) {
            SymbolTable classSymbolTable = classMap.get(functionDefinition.getScopeSpecification().getMember().getLexeme());
            classSymbolTable.addRow(functionRow);
        } else {
            System.err.println("ERROR! Can't find the class that this function should go in!");
        }

        scopeStack.add(functionBodySymbolTable);

        astTraversalStack.add(new ScopeBack());
    }

    /**
     * Go back in scope
     * @param scopeBack
     */
    @Override
    public void visitScopeBack(ScopeBack scopeBack) {
        scopeStack.pop();
    }

    /**
     * Add a variable row to the current scope
     * @param localVariableDeclaration
     */
    @Override
    public void visitLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration) {
        String variableType = localVariableDeclaration.getType().getMember().getLexeme();
        String variableName = localVariableDeclaration.getIdentifier().getMember().getLexeme();
        scopeStack.peek().addRow(new SymbolTableRow(variableName, SymbolTableRowKind.VARIABLE, variableType));
    }

    /**
     * Add a new class row with it's own scope
     * Also add rows for inherits
     * @param classDeclaration
     */
    @Override
    public void visitClassDeclaration(ClassDeclaration classDeclaration) {
        String className = classDeclaration.getIdentifier().getMember().getLexeme();

        SymbolTable classBodySymbolTable = new SymbolTable(className);

        scopeStack.peek().addRow(new SymbolTableRow(className, SymbolTableRowKind.CLASS, "", classBodySymbolTable));

        classMap.put(className, classBodySymbolTable);

        if (classDeclaration.getInheritanceList().getChildren().isEmpty()) {
            classBodySymbolTable.addRow(new SymbolTableRow("none", SymbolTableRowKind.INHERIT, ""));
        } else {
            for (SemanticConcept child : classDeclaration.getInheritanceList().getChildren()) {
                Identifier inherId = (Identifier) child;
                classBodySymbolTable.addRow(new SymbolTableRow(inherId.getMember().getLexeme(), SymbolTableRowKind.INHERIT, ""));
            }
        }
    }
}
