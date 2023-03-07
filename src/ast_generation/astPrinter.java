package ast_generation;

import ast_generation.tree.RelativeOperator;
import ast_generation.tree.SemanticConcept;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class astPrinter {
    private BufferedWriter dotFileWriter;
    private static int nextNodeId = 1;

    public astPrinter(String sourceFilePath) {
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String sourceFileName = outputDir.getFileName().toString();

        try {
            String astFileName = sourceFileName + ".dot.outast";
            File astFile = new File(outputDir + "/" + astFileName);
            dotFileWriter = new BufferedWriter(new FileWriter(astFile));

            dotFileWriter.write("digraph AST {\n"
                + "node [shape=record];\n"
                + "node [fontname=Sans];charset=\"UTF-8\" splines=true splines=spline rankdir =LR\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTree(SemanticConcept root) {
        writeNode(root);
        cleanup();
    }

    private int writeNode(SemanticConcept node) {
        int myId = nextNodeId;
        try {
            if (node.getMember() == null)
                dotFileWriter.write((nextNodeId++) + "[label=\"" + node.getName() +"\"];\n");
            else
                dotFileWriter.write((nextNodeId++) + "[label=\"" + node.getName() + " | " + ((!(node instanceof RelativeOperator)) ? node.getMember().getLexeme() : node.getMember().getToken().getName()) +"\"];\n");

            for (SemanticConcept child : node.getChildren())
                dotFileWriter.write(myId + "->" + writeNode(child) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myId;
    }

   private void cleanup() {
        try {
            dotFileWriter.write("}\n");
            dotFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
