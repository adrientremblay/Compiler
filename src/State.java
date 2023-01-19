import java.util.ArrayList;
import java.util.List;

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

    public List<State> epsilonClosure() {
        List<State> ret = new ArrayList<State>();
        ret.add(this);

        for (Edge edge : edges) {
            if (edge.label != "ε")
                continue;

            ret.add(edge.destination);
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
