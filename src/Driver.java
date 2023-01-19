import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.*;

public class Driver {
    public static void main(String args[]) throws IOException {
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("id", "letter alphanum*");
        testMap.put("alphanum", "letter | digit | _");
        testMap.put("integer", "nonzero digit* | 0");
        testMap.put("float", "integer fraction [e [+ | -] integer]");
        testMap.put("fraction", ". [digit* nonzero] | 0");

        ArrayList<String> testTokens = new ArrayList<String>();
        testTokens.add("id");
        testTokens.add("integer");
        testTokens.add("float");

        NdfaGenerator ndfaGenerator = new NdfaGenerator(testMap, testTokens);
        State entry = ndfaGenerator.generate();

        AutomataVisualizer automataVisualizer = new AutomataVisualizer();
        automataVisualizer.visualize(entry);
    }
}
