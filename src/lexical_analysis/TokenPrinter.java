package lexical_analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TokenPrinter {
    private Lexer lexer;

    public TokenPrinter(Lexer lexer) {
        this.lexer = lexer;
    }

    public void printTokens(String sourceFilePath) throws IOException {
        // reading and loading source
        String source = readFileAsString(sourceFilePath);
        lexer.loadSource(source);

        // creating file
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String inputFileName = outputDir.getFileName().toString();
        String outputFileName = inputFileName + ".outlextokens";
        File tokensOutputFile = new File(outputDir + "/" + outputFileName); // todo: make sure this works on windows
        BufferedWriter writer = new BufferedWriter(new FileWriter(tokensOutputFile));

        // reading tokens and writing to file
        int currentLine = 1;
        FoundToken t;
        while ((t = lexer.nextToken()).getToken() != Token.END_OF_FILE) {
            if (t.getFoundOnLine() > currentLine) {
                writer.write("\n");
                currentLine = t.getFoundOnLine();
            }
            writer.write(t.toString());
            writer.write(' ');
        }

        writer.close();
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
