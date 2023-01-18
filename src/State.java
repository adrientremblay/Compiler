import java.util.HashMap;

public class State {

    private HashMap<String, State> edges;

    public State() {
        edges = new HashMap<String, State>();
    }

    public void addEdge(String label, State target) {
        edges.put(label, target);
    }
}
