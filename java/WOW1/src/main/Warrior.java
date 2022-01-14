package main;

import java.util.NoSuchElementException;

public class Warrior {
    private String name;
    private int blood;

    private String getName() {
        return name;
    }

    public int getBlood() {
        return blood;
    }

    public static Warrior switchTable(String name, int blood) {
        switch (name) {
            case "dragon":
                return new Dragon(name, blood);
            case "ninja":
                return new Ninja(name, blood);
            case "iceman":
                return new Iceman(name, blood);
            case "lion":
                return new Lion(name, blood);
            case "wolf":
                return new Wolf(name, blood);
            default:
                throw new NoSuchElementException("Unknown warrior name");
        }
    }

    public Warrior(String name, int blood) {
        this.name = name;
        this.blood = blood;
    }

}
