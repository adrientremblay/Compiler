package syntactical_analysis;

import ast_generation.astPrinter;
import ast_generation.tree.*;
import ast_generation.tree.classes.*;
import lexical_analysis.FoundToken;
import lexical_analysis.Lexer;
import lexical_analysis.Token;
import util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Parser {
    private Lexer lexer;

    private HashMap<String, HashMap<String, String>> grammarTable;
    private HashMap<String, HashSet<String>> firstSets;
    private HashMap<String, HashSet<String>> followSets;
    private SyntaxDerivationPrinter syntaxDerivationPrinter;
    private astPrinter astPrinter;
    private String filepath;
    private boolean skippingErrors;

    private Stack<String> parseStack;
    private FoundToken foundToken;
    private FoundToken lastToken;
    private Program program;
    private FunctionDefinitionList functionDefinitionList;
    private ClassDeclarationList classDeclarationList;
    private Stack<SemanticConcept> semanticStack;

    public Parser() {
        lexer = new Lexer();

        GrammarTableGenerator grammarTableGenerator = new GrammarTableGenerator();
        grammarTable = grammarTableGenerator.generateGrammarTable();
        firstSets = grammarTableGenerator.getFirstSets();
        followSets = grammarTableGenerator.getFollowSets();
        filepath = "";
        skippingErrors = false;
    }

    public boolean parse() {
        System.out.println("Starting Parse.");

        program = new Program();
        functionDefinitionList = new FunctionDefinitionList();
        classDeclarationList = new ClassDeclarationList();
        program.addChild(functionDefinitionList);
        program.addChild(classDeclarationList);

        parseStack = new Stack<String>();

        parseStack.push("$");
        parseStack.push("<START>");

        foundToken = lexer.nextToken();

        semanticStack = new Stack<SemanticConcept>();

        String top;
        while ((top = parseStack.peek()) != "$") {
            // skips
            if (foundToken.getToken() == Token.IN_LINE_COMMENT || foundToken.getToken() == Token.BLOCK_COMMENT) {
                foundToken = lexer.nextToken();
                continue;
            }

            if (top.equals("EPSILON")) {
                parseStack.pop();
                continue;
            }

            // checking for semantic actions
            if (top.length() >= 1 && top.charAt(0) == '!') {
                String semanticAction = top.substring(1, top.length());
                switch (semanticAction) {
                    case "makeProgram": // todo: temp implementation
                       while (!semanticStack.isEmpty())
                            program.addChild(semanticStack.pop());
                       break;
                    case "makeIdentifier":
                        SemanticConcept identifier = new Identifier(lastToken);
                        semanticStack.add(identifier);
                        break;
                    case "makeAssExpr":
                        AssignmentStatement assignmentStatement = new AssignmentStatement(semanticStack.pop(), semanticStack.pop());
                        semanticStack.peek().addChild(assignmentStatement);
                        break;
                    case "makeInt":
                        Num number = new Num(lastToken);
                        semanticStack.push(number);
                        break;
                    case "makeMultiplyOperation":
                        semanticStack.push(new MultiplyOperation(semanticStack.pop()));
                        break;
                    case "addFactorToOp":
                        SemanticConcept factor = semanticStack.pop();
                        semanticStack.peek().addChild(factor);
                        break;
                    case "makeAdditionOperation":
                        semanticStack.push(new AdditionOperation(semanticStack.pop()));
                        break;
                    case "makeSubtractionOperation":
                        semanticStack.push(new SubtractionOperation(semanticStack.pop()));
                        break;
                    case "makeDivisionOperation":
                        semanticStack.push(new DivisionOperation(semanticStack.pop()));
                        break;
                    case "makeType":
                        semanticStack.push(new Type(lastToken));
                        break;
                    case "makeVariableDeclaration":
                        VariableDeclaration variableDeclaration = new VariableDeclaration(semanticStack.pop(), semanticStack.pop());
                        semanticStack.peek().addChild(variableDeclaration);
                        Dimensions dimensions = new Dimensions();
                        variableDeclaration.addChild(dimensions);
                        semanticStack.push(dimensions);
                        break;
                    case "addIntToTop":
                        semanticStack.peek().addChild(new Num(lastToken));
                        break;
                    case "makeFunctionDefinition":
                        functionDefinitionList.addChild(new FunctionDefinition(semanticStack.pop(), semanticStack.pop(), semanticStack.pop(), semanticStack.pop()));
                        break;
                    case "makeFunctionDeclaration":
                        FunctionDeclaration functionDeclaration = new FunctionDeclaration(semanticStack.pop(), semanticStack.pop(), semanticStack.pop());
                        semanticStack.peek().addChild(functionDeclaration);
                        break;
                    case "makeParameterList":
                        semanticStack.push(new ParameterList());
                        break;
                    case "pop":
                    case "closeDims":
                        semanticStack.pop();
                        break;
                    case "makeStatementBlock":
                        semanticStack.push(new StatementBlock());
                        break;
                    case "makeMemberList":
                        semanticStack.push(new MemberList());
                        break;
                    case "makeInheritanceList":
                        semanticStack.push(new InheritanceList());
                        break;
                    case "makeClassDeclaration":
                        classDeclarationList.addChild(new ClassDeclaration(semanticStack.pop(), semanticStack.pop(), semanticStack.pop()));
                        break;
                    case "consume":
                        SemanticConcept item = semanticStack.pop();
                        semanticStack.peek().addChild(item);
                        break;
                    default:
                        System.err.println("Unknown semantic action!!!!");
                        break;
               }
               parseStack.pop();
                continue;
            }

            if (GrammarTableGenerator.isTerminal(top)) {
                String topTerminal = top.substring(1, top.length() - 1);
                if (
                    topTerminal.equals(foundToken.getToken().getName()) // for reserved words
                    || topTerminal.equals(foundToken.getToken().getRegex()) // for everything else
                    || (topTerminal.equals("id") && foundToken.getToken().isType()) // type tokens should count as identifiers too
                ) {
                    // found a terminal
                    skippingErrors = false;
                    System.out.println("DEBUG: FOUND " + foundToken.getLexeme());
                    parseStack.pop();
                    lastToken = foundToken;
                    foundToken = lexer.nextToken();
                } else {
                    // did not find the terminal I wanted to... :'(
                    skipErrors();
                    continue;
                }
            } else {
                if (grammarTable.containsKey(top)) {
                    String rule;
                    if (grammarTable.get(top).containsKey(foundToken.getToken().getName())) {
                        rule = grammarTable.get(top).get(foundToken.getToken().getName());
                    } else if (grammarTable.get(top).containsKey(foundToken.getToken().getRegex())) { // todo: CRINGE!!
                        rule = grammarTable.get(top).get(foundToken.getToken().getRegex());
                    } else if (foundToken.getToken().equals(Token.END_OF_FILE) && grammarTable.get(top).containsKey("$")) {
                        rule = grammarTable.get(top).get("$");
                    } else {
                       // The cell is empty...
                       skipErrors();
                       continue;
                    }

                    skippingErrors = false;

                    parseStack.pop();

                    syntaxDerivationPrinter.writeLine(rule);
                    System.out.println(rule);

                    String[] ruleSplit = rule.split(" ");

                    String[] rightHandSide = Arrays.<String>copyOfRange(ruleSplit, 2, ruleSplit.length);
                    for (int i = rightHandSide.length - 1 ; i >= 0 ; i--) {
                        parseStack.push(rightHandSide[i]);
                    }
                } else {
                    // The rule isnt' even in the parse table???
                    skipErrors();
                    continue;
                }
            }
        }

        syntaxDerivationPrinter.cleanup();

        if (foundToken.getToken() != Token.END_OF_FILE)
            return false;

        System.out.println("Finished Parse.");

        // Print AST
        astPrinter.writeTree(program);

        return true;
    }

    public void loadSource(String sourceFilePath) {
        lexer.loadSource(Util.readFileAsString(sourceFilePath));
        syntaxDerivationPrinter = new SyntaxDerivationPrinter(sourceFilePath);
        astPrinter = new astPrinter(sourceFilePath);
        filepath = sourceFilePath;
    }

    private void skipErrors() {
        // Printing error message.
        if (!skippingErrors) {
            String errorMessage = new StringBuilder().append("Syntax Error in ").append(filepath).append(" ")
                    .append(lexer.getCurLine()).append(":").append(lexer.getCurChar()).append(" while parsing rule ")
                    .append(parseStack.peek()).append(", the token ").append(foundToken).append(" was found.")
                    .toString();

            System.err.println(errorMessage);
            syntaxDerivationPrinter.writeError(errorMessage);

            if (foundToken.getToken() == Token.END_OF_FILE
                    || (followSets.containsKey(parseStack.peek())) &&
                    (followSets.get(parseStack.peek()).contains(foundToken.getToken().getName())
                            || followSets.get(parseStack.peek()).contains(foundToken.getToken().getRegex()))) {
                parseStack.pop();
                return;
            }
        }

        foundToken = lexer.nextToken();
        skippingErrors = true;
    }
}
