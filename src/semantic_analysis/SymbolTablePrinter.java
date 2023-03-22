package semantic_analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class SymbolTablePrinter {
    private BufferedWriter symbolTableWriter;

    public SymbolTablePrinter(String sourceFilePath) {
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String sourceFileName = outputDir.getFileName().toString();

        try {
            String symbolTableFileName = sourceFileName + ".outsymboltables";
            File symbolTableOutputFile = new File(outputDir + "/" + symbolTableFileName);
            symbolTableWriter = new BufferedWriter(new FileWriter(symbolTableOutputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSymbolTable (SymbolTable symbolTable) {
        writeLine("- TABLE: " + symbolTable.getName());
        for (SymbolTableRow row : symbolTable.getRows()) {
            writeLine("  - " + row.getKind() + " | " + row.getName() + " | " + row.getType());
        }

        cleanup();
    }

    private void writeLine(String line) {
        try {
            symbolTableWriter.write(line + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanup() {
        try {
            symbolTableWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
