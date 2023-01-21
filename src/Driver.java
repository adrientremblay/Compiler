import lexical_analysis.Lexer;
import lexical_analysis.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Driver {
    public static void main(String args[]) throws IOException {
        Lexer lexer = new Lexer();

        String source = readFileAsString("test_source_files/basic/basic.src");

        lexer.loadSource(source);

        Token t;
        while ((t = lexer.nextToken()) != Token.END_OF_FILE) {
            System.out.println(t.getName());
        }

    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
