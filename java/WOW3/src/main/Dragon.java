package main;

public class Dragon extends Warrior {
    private double morale;

    public Dragon(String headquarter, String name, int index, int blood, int attack, int location, int bloodHeadquarter) {
        super(headquarter, name, index, blood, attack, location);

        Weapon weapon = Weapon.switchTable(index % 3, this);
        weaponList.add(weapon);

        this.morale = (double) bloodHeadquarter / blood;

    }

    public String bornMessage() {
        return "";
    }

    public void yell(int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(headquarter + " " + name + " " + index);
        mes.append(" yelled in city ").append(location);

        System.out.println(mes.toString());
    }

}
