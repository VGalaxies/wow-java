package main;

public class Weapon {
    private int index;
    private String name;
    private static String[] nameList = new String[]{"sword", "bomb", "arrow"};

    public Weapon(int index) {
        this.index = index;
        this.name = nameList[index];
    }

    public String getName() {
        return name;
    }
}
