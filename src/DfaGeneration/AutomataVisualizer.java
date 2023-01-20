package DfaGeneration;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static guru.nidi.graphviz.model.Factory.*;

public class AutomataVisualizer {
    /**
     * converts an DfaGeneration.Ndfa to a graphiz-java graph, and draws it
     */
    public static void visualize(State entry, String outputFileName) throws IOException {
        MutableGraph graph = mutGraph("example1")
                .setDirected(true)
                .graphAttrs().add(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT));

        int i = 1;
        Stack<StateNodePair> graphStack = new Stack<StateNodePair>();
        MutableNode baseNode = mutNode(String.valueOf("s" + i++));
        graphStack.add(new StateNodePair(entry, baseNode));
        HashMap<State, MutableNode> seen = new HashMap<State, MutableNode>();
        seen.put(entry, baseNode);

        while (!graphStack.empty()) {
            StateNodePair curPair = graphStack.pop();
            // add this state to the graph
            graph.add(curPair.node);

            ArrayList<State.Edge> curStateEdges = curPair.state.getEdges();
            for (State.Edge edge: curStateEdges) {
                State childState = edge.destination;

                MutableNode childNode;
                if (seen.containsKey(childState))
                    childNode = seen.get(childState);
                else
                    childNode = mutNode("s" + String.valueOf(i++));

                StateNodePair childPair = new StateNodePair(childState, childNode);

                // draw edge
                curPair.node.addLink(to(childPair.node).with(Label.of(edge.label)));

                // add the child to the stack
                if (!seen.containsKey(childState)) {
                    graphStack.push(childPair);
                    seen.put(childState, childNode);
                }
            }
        }

        Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File("images/" + outputFileName + ".png"));
    }

    private static class StateNodePair {
        public State state;
        public MutableNode node;

        public StateNodePair(State state, MutableNode node) {
            this.state = state;
            this.node = node;
        }
    }
}
