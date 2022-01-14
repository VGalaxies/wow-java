package main;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class Warrior {
    private String name;
    private int blood;

    protected ArrayList<Weapon> weaponList;

    private String getName() {
        return name;
    }

    public int getBlood() {
        return blood;
    }

    public static Warrior switchTable(String name, int blood, int indexWarrior, int bloodHeadquarter) {
        switch (name) {
            case "dragon":
                return new Dragon(name, blood, indexWarrior, bloodHeadquarter);
            case "ninja":
                return new Ninja(name, blood, indexWarrior);
            case "iceman":
                return new Iceman(name, blood, indexWarrior);
            case "lion":
                return new Lion(name, blood, bloodHeadquarter);
            case "wolf":
                return new Wolf(name, blood);
            default:
                throw new NoSuchElementException("Unknown warrior name");
        }
    }

    public Warrior(String name, int blood) {
        this.name = name;
        this.blood = blood;
        this.weaponList = new ArrayList<>();
    }


    public abstract String bornMessage();



}
