import java.util.*;

public class NdfaToDfaConvertor {
    public static DfaState convert(State dnfa) {
        HashMap<Set<State>, DfaState> dfa = new HashMap<Set<State>, DfaState>();
        Stack<DfaState> dnfaStateStack = new Stack<DfaState>();

        DfaState dfaRoot = new DfaState(dnfa.epsilonClosure());

        dnfaStateStack.push(dfaRoot);
        dfa.put(dfaRoot.getSlaves(), dfaRoot);

        while (! dnfaStateStack.empty()) {
            DfaState curState = dnfaStateStack.pop();
            HashMap<String, Set<State>> curStateMoves = curState.getMoves();
            for (String label : curStateMoves.keySet()) {
                Set<State> statesReached = curStateMoves.get(label);
                Set<State> statesReachedEpsilonClosure = epsilonClosureOfStateSet(statesReached);

                if (dfa.containsKey(statesReachedEpsilonClosure)) {
                    curState.addEdge(label, dfa.get(statesReachedEpsilonClosure));
                } else {
                    DfaState newState = new DfaState(statesReachedEpsilonClosure);
                    curState.addEdge(label, newState);

                    dnfaStateStack.push(newState);
                    dfa.put(newState.getSlaves(), newState);
                }
            }
        }

        return dfaRoot;
    }

    private static Set<State> epsilonClosureOfStateSet(Set<State> states) {
        HashSet<State> ret = new HashSet<State>();

        for (State state : states)
            ret.addAll(state.epsilonClosure());

        return ret;
    }

}
