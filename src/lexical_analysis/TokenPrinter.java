package lexical_analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TokenPrinter {
    private Lexer lexer;

    public TokenPrinter(Lexer lexer) {
        this.lexer = lexer;
    }


    public void printTokens(String sourceFilePath) throws IOException {
        String source = readFileAsString(sourceFilePath);
        lexer.loadSource(source);

        FoundToken t;
        while ((t = lexer.nextToken()).getToken() != Token.END_OF_FILE) {
            System.out.println(t);
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
