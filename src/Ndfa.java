public class Ndfa {
    private State entry;
    private State exit;

    public Ndfa(State entry, State exit) {
        this.entry = entry;
        this.exit = exit;
    }

    public State getEntry() {
        return entry;
    }

    public void setEntry(State entry) {
        this.entry = entry;
    }

    public State getExit() {
        return exit;
    }

    public void setExit(State exit) {
        this.exit = exit;
    }
}
