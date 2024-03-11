public class Transition {
    public final State from;
    public final State to;
    public final Character condition;

    public Transition(State from, State to, Character condition) {
        this.from = from;
        this.to = to;
        this.condition = condition;
    }
}
