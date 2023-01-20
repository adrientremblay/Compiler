package DfaGeneration;

import LexicalAnalysis.Token;

import java.util.*;

public class DfaState extends State {
    private Set<State> slaves;

    public DfaState(Set<State> slaves) {
        this.slaves = slaves;
    }

    public Set<State> getSlaves() {
        return slaves;
    }

    public HashMap<String, Set<State>> getMoves() {
        HashMap<String, Set<State>> ret = new HashMap<String, Set<State>>();

        for (State slave : slaves) {
            for (Edge edge : slave.getEdges()) {
                if (edge.label.equals("ε"))
                    continue;

                if (!ret.containsKey(edge.label))
                    ret.put(edge.label, new HashSet<State>());

                ret.get(edge.label).add(edge.destination);
            }
        }

        return ret;
    }

    @Override
    public Set<State> epsilonClosure() {
        Set<State> ret = new HashSet<State>();

        for (State slave : slaves)
            ret.addAll(slave.epsilonClosure());

        return ret;
    }

    @Override
    public Token getPathToken() {
        if (slaves.isEmpty())
            return null;

        return slaves.stream().findAny().get().getPathToken();
    }

    @Override
    public boolean isTerminal() {
        for (State slave : slaves)
            if (slave.isTerminal())
                return true;

        return false;
    }
}
