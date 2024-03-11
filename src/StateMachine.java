import java.util.HashSet;
import java.util.Set;

public class StateMachine {
    public Set<State> states;
    public Set<Character> characters;
    public Set<Transition> transitions;

    public StateMachine() {
        this.states = new HashSet<>();
        this.characters = new HashSet<>();
        this.transitions = new HashSet<>();
    }

    @Override
    public String toString() {
        return "StateMachine{" +
                statesToString() +
                charactersToString() +
                transitionsToString() +
                '}';
    }

    private String statesToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("States=[");
        for (State state : states) {
            builder.append(state.name).append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    private String charactersToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Characters=[");
        for (Character character : characters) {
            builder.append(character.symbol).append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    private String transitionsToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Transitions=[");
        for (Transition transition : transitions) {
            builder.append(transition.from.name).append("->").append(transition.to.name).append("(").append(transition.condition.symbol).append("), ");
        }
        builder.append("]");
        return builder.toString();
    }


}
