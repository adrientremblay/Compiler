import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import static guru.nidi.graphviz.model.Factory.*;

public class AutomataVisualizer {
    /**
     * converts an Ndfa to a graphiz-java graph, and draws it
     */
    public void visualize(State entry) throws IOException {
        MutableGraph graph = mutGraph("example1").setDirected(true);

        Stack<State> stateStack = new Stack<State>();
        stateStack.add(entry);

        int i = 1;
        while (!stateStack.empty()) {
            State state = stateStack.pop();
            // add this state to the graph
            graph.add(node(String.valueOf(i++)));

            // add it's children to the stack
            HashMap<String, State> stateEdges = state.getEdges();
            for (String label: stateEdges.keySet()) {
                stateStack.push(stateEdges.get(label));
            }
        }

        Graphviz.fromGraph(graph).width(500).render(Format.PNG).toFile(new File("example/ex1m.png"));
    }
}
