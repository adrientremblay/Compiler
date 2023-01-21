import lexical_analysis.Lexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {
    public static void main(String args[]) throws IOException {
        Lexer lexer = new Lexer();

        String source = readFileAsString("test_source_files/basic/basic.src");

        lexer.loadSource(source);

        System.out.println(lexer.nextToken().getName());
        System.out.println(lexer.nextToken().getName());
        System.out.println(lexer.nextToken().getName());
        System.out.println(lexer.nextToken().getName());
        System.out.println(lexer.nextToken().getName());
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
