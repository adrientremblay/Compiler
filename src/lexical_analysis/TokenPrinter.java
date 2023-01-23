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

        // creating files
        Path sourceFilePathAsPath = Path.of(sourceFilePath);
        Path outputDir = sourceFilePathAsPath.getParent();
        String sourceFileName = outputDir.getFileName().toString();

        String tokensFileName = sourceFileName + ".outlextokens";
        File tokensOutputFile = new File(outputDir + "/" + tokensFileName); // todo: make sure this works on windows
        BufferedWriter tokensWriter = new BufferedWriter(new FileWriter(tokensOutputFile));

        String errorsFileName = sourceFileName + ".outlexerrors";
        File errorsOutputFile = new File(outputDir + "/" + errorsFileName); // todo: make sure this works on windows
        BufferedWriter errorsWriter = new BufferedWriter(new FileWriter(errorsOutputFile));

        // reading tokens and writing to file
        int currentLine = 1;
        FoundToken t;
        while ((t = lexer.nextToken()).getToken() != Token.END_OF_FILE) {
            if (t.getFoundOnLine() > currentLine) {
                tokensWriter.write("\n");
                currentLine = t.getFoundOnLine();
            }
            tokensWriter.write(t.toString());
            tokensWriter.write(' ');

            if (t.getToken() == Token.ERROR || t.getToken() == Token.INVALID_CHAR) {
                errorsWriter.write("Lexical error: ");
                switch (t.getToken()) {
                    case ERROR -> errorsWriter.write("Error: ");
                    case INVALID_CHAR -> errorsWriter.write("Invalid character: ");
                }
                errorsWriter.write("\"" + t.getLexeme()  +"\": ");
                errorsWriter.write("line " + String.valueOf(t.getFoundOnLine() + ", "));
                errorsWriter.write("character " + String.valueOf(t.getFoundOnChar() + ".\n"));
            }
        }

        tokensWriter.close();
        errorsWriter.close();
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Path.of(fileName)));
    }
}
