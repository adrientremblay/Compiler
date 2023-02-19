package syntactical_analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class SyntaxDerivationPrinter {
    BufferedWriter derivationWriter;

    public SyntaxDerivationPrinter(String sourceFilePath) {
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String sourceFileName = outputDir.getFileName().toString();

        try {
            String derivationFileName = sourceFileName + ".derivation";
            File derivationOutputFile = new File(outputDir + "/" + derivationFileName);
            derivationWriter = new BufferedWriter(new FileWriter(derivationFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String line) {
        try {
            derivationWriter.write(line + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanup() {
        try {
            derivationWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
