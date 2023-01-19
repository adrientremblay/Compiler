import com.kitfox.svg.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DfaState extends State {
    private List<State> slaves;

    public DfaState(List<State> slaves) {
        this.slaves = slaves;
    }

    public List<State> getSlaves() {
        return slaves;
    }

    @Override
    public List<State> epsilonClosure() {
        ArrayList<State> ret = new ArrayList<State>();

        for (State slave : slaves)
            ret.addAll(slave.epsilonClosure());

        return ret;
    }
}
