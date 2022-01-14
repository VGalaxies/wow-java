package main;

public class Arrow extends Weapon{
    int durability = 2;

    public Arrow(int index, Warrior user) {
        super(index, user);
    }

    @Override
    public void attack(Warrior other) {
        if (durability >= 0) {
            durability--;
            int attack = (int) Math.floor(user.attack * 0.3);
            if (other.blood  < attack) {
                other.blood = 0;
            } else {
                other.blood -= attack;
            }
        }
    }

}
