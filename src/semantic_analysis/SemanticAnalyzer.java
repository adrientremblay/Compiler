package semantic_analysis;

import syntactical_analysis.ast_generation.tree.FunctionDefinition;
import syntactical_analysis.ast_generation.tree.Identifier;
import syntactical_analysis.ast_generation.tree.Program;
import syntactical_analysis.ast_generation.tree.SemanticConcept;
import syntactical_analysis.Parser;
import syntactical_analysis.ast_generation.tree.classes.ClassDeclaration;
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
        astTraversalStack.push(ast);

        scopeStack = new Stack<SymbolTable>();

        classMap = new HashMap<String, SymbolTable>();

        while (!astTraversalStack.isEmpty()) {
            SemanticConcept curNode = astTraversalStack.pop();
            curNode.accept(this);
            astTraversalStack.addAll(curNode.getChildren());
        }

        symbolTablePrinter.writeGlobalSymbolTable(globalSymbolTable);

        return globalSymbolTable;
    }

    /**
     * Generates a new global table
     *
     * @param program
     */
    @Override
    public void visitProgram(Program program) {
        globalSymbolTable = new SymbolTable("global");
        scopeStack.add(globalSymbolTable);
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

        SymbolTable functionBodySymbolTable = new SymbolTable(functionName);

        scopeStack.peek().addRow(new SymbolTableRow(functionName, SymbolTableRowKind.FUNCTION, functionType, functionBodySymbolTable));

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
