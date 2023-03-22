package lexical_analysis.dfa_generation;

import lexical_analysis.Token;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

// todo: state should really be an interface cause for DfaGeneration.DfaState edges should be only other Dfa States
public class State {
    private HashSet<Edge> edges;
    private Token pathToken;

    public State() {
        edges = new HashSet<Edge>();
    }

    public State(Token pathToken) {
        this();
        this.pathToken = pathToken;
    }

    public void addEdge(String label, State target) {
        edges.add(new Edge(label, target));
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Set<State> epsilonClosure() {
        Set<State> ret = new HashSet<State>();

        Stack<State> toExplore = new Stack<State>();
        toExplore.add(this);

        while (! toExplore.empty()) {
            State state = toExplore.pop();
            ret.add(state);

            for (Edge edge : state.edges) {
                if (edge.label != "ε")
                    continue;

                if (toExplore.contains(edge.destination) || ret.contains(edge.destination))
                    continue;

                toExplore.push(edge.destination);
            }
        }

        return ret;
    }

    public boolean isTerminal() {
        return edges.isEmpty();
    }

    public Token getPathToken() {
        return pathToken;
    }

    public class Edge {
       public String label;
       public State destination;

        public Edge(String label, State destination) {
            this.label = label;
            this.destination = destination;
        }
    }
}
