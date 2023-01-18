import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import static guru.nidi.graphviz.model.Factory.*;

public class AutomataVisualizer {
    /**
     * converts an Ndfa to a graphiz-java graph, and draws it
     */
    public void visualize(State entry) throws IOException {
        MutableGraph graph = mutGraph("example1").setDirected(true);

        int i = 1;
        Stack<StateNodePair> graphStack = new Stack<StateNodePair>();
        Node baseNode = node(String.valueOf(i++));
        graphStack.add(new StateNodePair(entry, baseNode));
        HashMap<State, Node> seen = new HashMap<State, Node>();
        seen.put(entry, baseNode);

        while (!graphStack.empty()) {
            StateNodePair curPair = graphStack.pop();
            // add this state to the graph
            graph.add(curPair.node);

            HashMap<String, State> stateEdges = curPair.state.getEdges();
            for (String label: stateEdges.keySet()) {
                State childState = stateEdges.get(label);

                Node childNode;
                if (seen.containsKey(childState))
                    childNode = seen.get(childState);
                else
                    childNode = node(String.valueOf(i++));

                StateNodePair childPair = new StateNodePair(childState, childNode);

                // draw edge
                // todo: use label
                curPair.node.link(childPair.node);

                // add the child to the stack
                if (!seen.containsKey(childState)) {
                    graphStack.push(childPair);
                    seen.put(childState, childNode);
                }
            }
        }

        Graphviz.fromGraph(graph).width(500).render(Format.PNG).toFile(new File("example/ex1m.png"));
    }

    private class StateNodePair {
        public State state;
        public Node node;

        public StateNodePair(State state, Node node) {
            this.state = state;
            this.node = node;
        }
    }
}
