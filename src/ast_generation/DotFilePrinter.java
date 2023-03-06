package ast_generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class DotFilePrinter {
    private BufferedWriter dotFileWriter;
    private static int nextNodeId = 1;

    public DotFilePrinter(String sourceFilePath) {
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

    public void writeNode(String name) {
        try {
            dotFileWriter.write((nextNodeId++) + "[label=\"" + name +"\"];\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanup() {
        try {
            dotFileWriter.write("}\n");
            dotFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
