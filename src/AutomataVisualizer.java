import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.*;

public class AutomataVisualizer {
    /**
     * converts an Ndfa to a graphiz-java graph, and draws it
     */
    public void visualize(State entry) throws IOException {
        MutableGraph graph = mutGraph("example1").setDirected(true);

        graph.add(node("main"));

        Graphviz.fromGraph(graph).width(200).render(Format.PNG).toFile(new File("example/ex1m.png"));
    }
}
