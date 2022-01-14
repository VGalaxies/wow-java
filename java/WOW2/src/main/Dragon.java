package main;

public class Dragon extends Warrior {
    private double morale;

    public Dragon(String name, int blood, int indexWarrior, int bloodHeadquarter) {
        super(name, blood);
        Weapon weapon = new Weapon(indexWarrior % 3);
        this.morale = (double) bloodHeadquarter / blood;
        weaponList.add(weapon);
    }

    public String bornMessage() {
        return "It has a " + weaponList.get(0).getName()
                + ",and it's morale is " + String.format("%.2f", morale);
    }

}
