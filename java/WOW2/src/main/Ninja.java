package main;

public class Ninja extends Warrior {
    public Ninja(String name, int blood, int indexWarrior) {
        super(name, blood);
        Weapon weapon1 = new Weapon(indexWarrior % 3);
        Weapon weapon2 = new Weapon((indexWarrior + 1) % 3);
        weaponList.add(weapon1);
        weaponList.add(weapon2);
    }

    public String bornMessage() {
        return "It has a " + weaponList.get(0).getName()
                + " and a " + weaponList.get(1).getName();
    }
}
