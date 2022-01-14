package main;

public class Lion extends Warrior{
    private int loyalty;
    private int loyaltyDecrease;

    public Lion(String headquarter, String name, int index, int blood, int attack, int location, int bloodHeadquarter,int loyaltyDecrease) {
        super(headquarter, name, index, blood, attack, location);

        Weapon weapon = Weapon.switchTable(index % 3, this);
        weaponList.add(weapon);

        this.loyalty = bloodHeadquarter;
        this.loyaltyDecrease = loyaltyDecrease;
    }

    public String bornMessage() {
        return "Its loyalty is " + loyalty;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    public int getLoyaltyDecrease() {
        return loyaltyDecrease;
    }

    public void escape(int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(headquarter + " lion " + index + " ran away");

        System.out.println(mes.toString());
    }

}
