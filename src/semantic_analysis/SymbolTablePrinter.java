package semantic_analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class SymbolTablePrinter {
    private BufferedWriter symbolTableWriter;
    private BufferedWriter semanticErrorWriter;

    // todo: the order of the rows is kinda messed up because of my use of Sets for children... fix this?
    public SymbolTablePrinter(String sourceFilePath) {
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String sourceFileName = outputDir.getFileName().toString();

        try {
            String symbolTableFileName = sourceFileName + ".outsymboltables";
            File symbolTableOutputFile = new File(outputDir + "/" + symbolTableFileName);
            symbolTableWriter = new BufferedWriter(new FileWriter(symbolTableOutputFile));

            String semanticErrorsFileName = sourceFileName + ".outsemanticerrors";
            File semanticErrorsOutputFile = new File(outputDir + "/" + semanticErrorsFileName);
            semanticErrorWriter = new BufferedWriter(new FileWriter(semanticErrorsOutputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeGlobalSymbolTable(SymbolTable globalSymbolTable) {
        writeSymbolTable(globalSymbolTable, 0);

        cleanup();
    }

    private void writeSymbolTable(SymbolTable symbolTable, int indent) {
        StringBuilder sb = new StringBuilder();
        int toIndent = indent;
        while (toIndent > 0) {
            sb.append("\t");
            toIndent--;
        }
        String spacing = sb.toString();

        //writeLine(spacing + "-----------------------------------------------------------");
        writeLine(spacing + "- TABLE: " + symbolTable.getName());
        //writeLine(spacing + "-----------------------------------------------------------");
        for (SymbolTableRow row : symbolTable.getRows()) {
            String visibilityExtension = (row.getVisibilityKind() != null) ? " | " + row.getVisibilityKind().toString() : "";

            writeLine(spacing + "- " + row.getRowKind() + " | " + row.getName() + " | " + row.getType() + visibilityExtension);
            if (row.getSymbolTableLink() != null) {
                writeSymbolTable(row.getSymbolTableLink(), indent+1);
            }
        }
        //writeLine(spacing + "-----------------------------------------------------------");
    }

    private void writeLine(String line) {
        try {
            symbolTableWriter.write(line + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeError(String line) {
        try {
            semanticErrorWriter.write(line + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanup() {
        try {
            symbolTableWriter.close();
            semanticErrorWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
