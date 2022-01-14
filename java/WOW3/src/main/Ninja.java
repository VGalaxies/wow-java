package main;

public class Ninja extends Warrior {
    public Ninja(String headquarter, String name, int index, int blood, int attack, int location) {
        super(headquarter, name, index, blood, attack, location);
        Weapon weapon = Weapon.switchTable(index % 3, this);
        weaponList.add(weapon);
        weapon = Weapon.switchTable((index + 1) % 3, this);
        weaponList.add(weapon);
    }

    public String bornMessage() {
        return "";
    }
}
