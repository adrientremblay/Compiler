import LexicalAnalysis.Lexer;

import java.io.IOException;

public class Driver {
    public static void main(String args[]) throws IOException {
        Lexer lexer = new Lexer();
        lexer.loadSource("cedric/ 123");

        System.out.println(lexer.nextToken().getName());
        System.out.println(lexer.nextToken().getName());
        System.out.println(lexer.nextToken().getName());
    }
}
