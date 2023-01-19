import java.util.*;

public class NdfaToDfaConvertor {
    public static DfaState convert(State dnfa) {
        HashMap<List<State>, DfaState> dfa = new HashMap<List<State>, DfaState>();
        Stack<DfaState> dnfaStateStack = new Stack<DfaState>();

        DfaState dfaRoot = new DfaState(dnfa.epsilonClosure());

        dnfaStateStack.push(dfaRoot);
        dfa.put(dfaRoot.getSlaves(), dfaRoot);

        if (! dnfaStateStack.empty()) {
            DfaState curState = dnfaStateStack.pop();
            for (State.Edge edge : curState.getEdges()) {
                if (edge.label.equals("ε"))
                    continue;

                List<State> potentialNewStateEpsilonClosure = edge.destination.epsilonClosure();

                if (dfa.containsKey(potentialNewStateEpsilonClosure)) {
                    curState.addEdge(edge.label, dfa.get(potentialNewStateEpsilonClosure));
                } else {
                    DfaState newState = new DfaState(potentialNewStateEpsilonClosure);
                    curState.addEdge(edge.label, newState);

                    dnfaStateStack.push(newState);
                    dfa.put(newState.getSlaves(), newState);
                }
            }
        }

        return dfaRoot;
    }

}
