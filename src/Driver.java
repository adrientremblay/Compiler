import lexical_analysis.FoundToken;
import lexical_analysis.Lexer;
import lexical_analysis.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Driver {
    public static void main(String args[]) throws IOException {
        Lexer lexer = new Lexer();

        String source = readFileAsString("test_source_files/bubble_sort/bubble_sort.src");

        lexer.loadSource(source);

        FoundToken t;
        while ((t = lexer.nextToken()).getToken() != Token.END_OF_FILE) {
            System.out.println(t);
        }
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
