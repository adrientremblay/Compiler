import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

// todo: state should really be an intercae cause for DfaState edges should be only other Dfa States
public class State {
    private ArrayList<Edge> edges;

    public State() {
        edges = new ArrayList<Edge>();
    }

    public void addEdge(String label, State target) {
        edges.add(new Edge(label, target));
    }

    public ArrayList<Edge> getEdges() {
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

    public class Edge {
       public String label;
       public State destination;

        public Edge(String label, State destination) {
            this.label = label;
            this.destination = destination;
        }
    }
}
