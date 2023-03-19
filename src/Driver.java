import syntactical_analysis.Parser;

import java.io.IOException;

import static util.Util.readFileAsString;

/**
 * I would have used JUnit to separate my test cases but the assignment asks for a Driver class
 */
public class Driver {
    private static final String[] SOURCE_FILES = {
            //        "test_source_files/my_test/my_test.src",
            //        "test_source_files/bubble_sort/bubble_sort.src",
            //        "test_source_files/bubble_sort_extended/bubble_sort_extended.src",
            //        "test_source_files/lex_negative_grading/lex_negative_grading.src",
            //        "test_source_files/lex_positive_grading/lex_positive_grading.src",
                    "test_source_files/polynomial/polynomial.src"
            // "test_source_files/syntax_errors_galore/syntax_errors_galore.src",
            // "test_source_files/ast_test/ast_test.src",
    };

    public static void main(String args[]) throws IOException {
        Parser parser = new Parser();

        for (String sourceFilePath : SOURCE_FILES) {
            parser.loadSource(sourceFilePath);
            parser.parse();
        }
    }
}
