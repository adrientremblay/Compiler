package semantic_analysis;

import syntactical_analysis.ast_generation.tree.FunctionDefinition;
import syntactical_analysis.ast_generation.tree.Program;
import syntactical_analysis.ast_generation.tree.SemanticConcept;
import syntactical_analysis.Parser;
import syntactical_analysis.ast_generation.tree.statements.LocalVariableDeclaration;

import java.util.Stack;

public class SemanticAnalyzer implements SymbolTableVisitor {
    private Parser parser;

    private SymbolTablePrinter symbolTablePrinter;
    private SymbolTable globalSymbolTable;
    private Stack<SymbolTable> scopeStack;
    private Stack<SemanticConcept> astTraversalStack;

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
        String functionType = functionDefinition.getParameterList().toString() + ":" + functionDefinition.getType().getMember().getLexeme();

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
}
