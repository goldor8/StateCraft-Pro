public class State {
    public final String name;

    public State(String name) {
        if (Utils.checkSpecialCharacter(name)) throw new RuntimeException("Invalid state name");
        this.name = name;
    }
}
