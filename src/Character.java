public class Character {
    public final String symbol;

    public Character(String name) {
        if (Utils.checkSpecialCharacter(name)) throw new RuntimeException("Invalid character name");
        this.symbol = name;
    }
}
