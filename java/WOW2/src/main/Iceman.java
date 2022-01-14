package main;

public class Iceman extends Warrior{
    public Iceman(String name, int blood, int indexWarrior) {
        super(name, blood);
        Weapon weapon = new Weapon(indexWarrior % 3);
        weaponList.add(weapon);
    }

    public String bornMessage() {
        return "It has a " + weaponList.get(0).getName();
    }

}
