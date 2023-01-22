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
        Path.of(sourceFilePath).getRoot();
        Path.of(sourceFilePath).getParent();

        File testFile = new File(Path.of(sourceFilePath).getParent() + "/penis.text"); // todo: make sure this works on windows
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("penis");

        FoundToken t;
        while ((t = lexer.nextToken()).getToken() != Token.END_OF_FILE) {
            System.out.println(t);
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
