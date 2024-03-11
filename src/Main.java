public class Main {
    public static void main(String[] args) {
        create();
        load();
    }

    private static void create() {
        StateMachine stateMachine = new StateMachine();

        State q0 = new State("q0");
        State q1 = new State("q1");
        State q2 = new State("q2");

        stateMachine.states.add(q0);
        stateMachine.states.add(q1);
        stateMachine.states.add(q2);

        Character a = new Character("a");
        Character b = new Character("b");

        stateMachine.characters.add(a);
        stateMachine.characters.add(b);

        stateMachine.transitions.add(new Transition(q0, q1, a));
        stateMachine.transitions.add(new Transition(q1, q2, b));
        stateMachine.transitions.add(new Transition(q2, q0, a));

        System.out.println(stateMachine);
        StateMachineLoader.save("StateMachineStorage/state-machine.txt", stateMachine);
    }

    private static void load() {
        StateMachine stateMachine = StateMachineLoader.load("StateMachineStorage/state-machine.txt");
        System.out.println(stateMachine);
    }
}