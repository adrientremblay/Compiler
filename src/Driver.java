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
        //testMap.put("digit", "0.009");
        // todo: need to figure out this I don't want to expand

        ArrayList<String> testTokens = new ArrayList<String>();
        testTokens.add("id");

        NdfaGenerator ndfaGenerator = new NdfaGenerator(testMap, testTokens);
        ndfaGenerator.generate();

        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                .nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(
                        node("a").with(Color.RED).link(node("b")),
                        node("b").link(
                                to(node("c")).with(attr("weight", 5), Style.DASHED)
                        )
                );
        Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(new File("example/ex1.png"));
    }
}
