package LexicalAnalysis;

import DfaGeneration.*;

import java.io.IOException;

/**
 * Actually a stateful class
 */
public class Lexer {
    private NdfaGenerator ndfaGenerator;
    private DfaState dfa;
    private String sourceCode;
    private int sourceIndex;

    public Lexer() throws IOException {
        ndfaGenerator = new NdfaGenerator();

        ndfaGenerator.addElementRegularExpression("id", "letter alphanum*");
        ndfaGenerator.addElementRegularExpression("alphanum", "letter | digit | _");
        ndfaGenerator.addElementRegularExpression("integer", "[nonzero digit*] | 0");
        ndfaGenerator.addElementRegularExpression("float", "integer fraction [e [+ | -] integer]");
        ndfaGenerator.addElementRegularExpression("fraction", ". [digit* nonzero] | 0");

        ndfaGenerator.addTokens("id", "integer", "float");

        State ndfa = ndfaGenerator.generate();
        AutomataVisualizer.visualize(ndfa, "ndfa");

        dfa = NdfaToDfaConvertor.convert(ndfa);
        AutomataVisualizer.visualize(dfa, "dfa");
    }

    public void loadSource(String source) {
        this.sourceCode = source;
        sourceIndex = 0;
    }

    public Token nextToken() {
        DfaState cur = dfa;


        return null;
    }
}
