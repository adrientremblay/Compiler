import java.util.ArrayList;
import java.util.HashMap;

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

    public class Edge {
       public String label;
       public State destination;

        public Edge(String label, State destination) {
            this.label = label;
            this.destination = destination;
        }
    }
}
