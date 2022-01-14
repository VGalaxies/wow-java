package main;

public class Bomb extends Weapon {
    int durability = 1;

    public Bomb(int index, Warrior user) {
        super(index, user);
    }

    @Override
    public void attack(Warrior other) {
        if (durability >= 0) {
            durability--;

            int attack = (int) Math.floor(user.attack * 0.4);
            if (other.blood < attack) {
                other.blood = 0;
            } else {
                other.blood -= attack;
            }

            if (! (user instanceof Ninja)) {
                int selfAttack = (int) Math.floor(attack * 0.5);
                if (user.blood < selfAttack) {
                    user.blood = 0;
                } else {
                    user.blood -= selfAttack;
                }
            }
        }
    }
}
