package syntactical_analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class SyntaxDerivationPrinter {
    private BufferedWriter derivationWriter;
    private BufferedWriter syntaxErrorWriter;

    public SyntaxDerivationPrinter(String sourceFilePath) {
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String sourceFileName = outputDir.getFileName().toString();

        try {
            String derivationFileName = sourceFileName + ".derivation";
            File derivationOutputFile = new File(outputDir + "/" + derivationFileName);
            derivationWriter = new BufferedWriter(new FileWriter(derivationOutputFile));

            String syntaxErrorsFileName = sourceFileName + ".outsyntaxerrors";
            File syntaxErrorsOutputFile = new File(outputDir + "/" + syntaxErrorsFileName);
            syntaxErrorWriter = new BufferedWriter(new FileWriter(syntaxErrorsOutputFile));
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

    public void writeError(String line) {
        try {
            syntaxErrorWriter.write(line + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanup() {
        try {
            derivationWriter.close();
            syntaxErrorWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
