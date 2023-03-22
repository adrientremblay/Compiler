package semantic_analysis;

import ast_generation.tree.Program;
import ast_generation.tree.SemanticConcept;
import syntactical_analysis.Parser;

import java.util.Stack;

public class SemanticAnalyzer {
    private Parser parser;

    private SymbolTablePrinter symbolTablePrinter;

    public SemanticAnalyzer() {
        parser = new Parser();
    }

    public void loadSource(String sourceFilePath) {
        parser.loadSource(sourceFilePath);
        symbolTablePrinter = new SymbolTablePrinter(sourceFilePath);
    }

    public SymbolTable generateSymbolTable() {
        Program ast = parser.parse();

        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();

        Stack<SemanticConcept> astTraversalStack = new Stack<>();
        astTraversalStack.push(ast);

        while (!astTraversalStack.isEmpty()) {
            SemanticConcept curNode = astTraversalStack.pop();
            curNode.accept(symbolTableVisitor);
            astTraversalStack.addAll(curNode.getChildren());
        }

        SymbolTable globalSymbolTable = symbolTableVisitor.getGlobalSymbolTable();

        symbolTablePrinter.writeSymbolTable(globalSymbolTable);

        return globalSymbolTable;
    }
}
