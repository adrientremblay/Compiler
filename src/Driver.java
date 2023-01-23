import lexical_analysis.Lexer;
import lexical_analysis.TokenPrinter;

import java.io.IOException;


/**
 * I would have used JUnit to separate my test cases but the assignment asks for a Driver class
 */
public class Driver {
    public static void main(String args[]) throws IOException {
        Lexer lexer = new Lexer();
        TokenPrinter tokenPrinter = new TokenPrinter(lexer);

        String[] sourceFilePaths = {
                //"test_source_files/basic/basic.src",
                //"test_source_files/bubble_sort/bubble_sort.src",
                "test_source_files/lex_negative_grading/lex_negative_grading.src",
                //"test_source_files/lex_positive_grading/lex_positive_grading.src",
                //"test_source_files/polynomial/polynomial.src"
        };

        for (String sourceFilePath : sourceFilePaths)
            tokenPrinter.printTokens(sourceFilePath);
    }
}
