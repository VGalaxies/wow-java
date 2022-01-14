package main;

public class Sword extends Weapon{
    public Sword(int index, Warrior user) {
        super(index, user);
    }

    @Override
    public void attack(Warrior other) {
        int attack = (int) Math.floor(user.attack * 0.2);
        if (other.blood  < attack) {
            other.blood = 0;
        } else {
            other.blood -= attack;
        }
    }
}
