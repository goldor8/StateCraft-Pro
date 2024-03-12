import java.io.*;

public class StateMachineLoader {
    private enum SECTION {
        STATES,
        CHARACTERS,
        TRANSITIONS
    }

    public static void save(String path, StateMachine stateMachine) {
        File file = new File(path);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(serialize(stateMachine));
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String serialize(StateMachine stateMachine) {
        StringBuilder builder = new StringBuilder();
        builder.append(SECTION.STATES.name()).append(":\n");
        for (State state : stateMachine.states) {
            builder.append("-name:").append(state.name).append("\n");
        }

        builder.append(SECTION.CHARACTERS.name()).append(":\n");
        for (Character character : stateMachine.characters) {
            builder.append("-symbol:").append(character.symbol).append("\n");
        }

        builder.append(SECTION.TRANSITIONS.name()).append(":\n");
        for (Transition transition : stateMachine.transitions) {
            builder.append("-from:").append(transition.from.name).append(",to:").append(transition.to.name).append(",condition:").append(transition.condition.symbol).append("\n");
        }

        return builder.toString();
    }

    private static SECTION checkSection(String line) {
        for (SECTION section : SECTION.values()) {
            if (line.equals(section.name() + ":")) {
                return section;
            }
        }
        return null;
    }

    public static StateMachine deserialize(String content) {
        StateMachine stateMachine = new StateMachine();

        String[] lines = content.split("\n");
        SECTION currentSection = null;
        for (String line : lines) {
            SECTION possibleSection = checkSection(line);
            if (possibleSection != null) {
                currentSection = possibleSection;
                continue;
            }

            if (currentSection == null) {
                throw new RuntimeException("Invalid file format");
            }

            switch (currentSection) {
                case STATES -> {
                    if (line.startsWith("-name:")) {
                        String name = line.split(":")[1];
                        stateMachine.states.add(new State(name));
                    } else {
                        throw new RuntimeException("Invalid file format");
                    }
                }
                case CHARACTERS -> {
                    if (line.startsWith("-symbol:")) {
                        String symbol = line.split(":")[1];
                        stateMachine.characters.add(new Character(symbol));
                    } else {
                        throw new RuntimeException("Invalid file format");
                    }
                }
                case TRANSITIONS -> {
                    if (line.startsWith("-from:")) {
                        String[] parts = line.split(",");
                        String from = parts[0].split(":")[1];
                        String to = parts[1].split(":")[1];
                        String condition = parts[2].split(":")[1];

                        State fromState = null;
                        State toState = null;
                        Character conditionChar = null;
                        for (State state : stateMachine.states) {
                            if(state.name.equals(from)){
                                fromState = state;
                            }
                            if(state.name.equals(to)){
                                toState = state;
                            }
                        }

                        for (Character character : stateMachine.characters) {
                            if(character.symbol.equals(condition)){
                                conditionChar = character;
                            }
                        }

                        if(fromState == null || toState == null || conditionChar == null) throw new RuntimeException("Invalid file format (missing state or character)");

                        stateMachine.transitions.add(new Transition(fromState, toState, conditionChar));
                    } else {
                        throw new RuntimeException("Invalid file format");
                    }
                }
                default -> throw new RuntimeException("Invalid file format");
            }
        }

        return stateMachine;
    }

    public static StateMachine load(String path) {
        File file = new File(path);
        try {
            InputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
            reader.close();
            return deserialize(builder.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
