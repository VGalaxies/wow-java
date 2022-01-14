package main;

import java.util.Comparator;
import java.util.NoSuchElementException;

public abstract class Weapon{
    protected Warrior user;
    int index;
    private String name;
    private static String[] nameList = new String[]{"sword", "bomb", "arrow"};

    public Weapon(int index, Warrior user) {
        this.index = index;
        this.user = user;
        this.name = nameList[index];
    }

    public String getName() {
        return name;
    }

    public abstract void attack(Warrior other);

    public static Weapon switchTable(int index, Warrior user) {
        switch (index) {
            case 0:
                return new Sword(index, user);
            case 1:
                return new Bomb(index, user);
            case 2:
                return new Arrow(index, user);
            default:
                throw new NoSuchElementException("Unknown weapon name");
        }
    }

    public int getIndex() {
        return index;
    }

    public static String[] getNameList() {
        return nameList;
    }
}

class battleOrder implements Comparator<Weapon> {
    public int compare(Weapon first, Weapon second) {
        if (first.index < second.index)
            return -1;
        else if (first.index == second.index) {
            if (first instanceof Arrow && second instanceof Arrow) {
                Arrow w = (Arrow) first;
                Arrow v = (Arrow) second;
                // 没用过的排在前面
                if (w.durability < v.durability) {
                    return 1;
                } else if (w.durability == v.durability) {
                    return 0;
                } else {
                    return -1;
                }
            }
            return 0;
        }

        else
            return 1;
    }
}

class stealOrder implements Comparator<Weapon> {
    public int compare(Weapon first, Weapon second) {
        if (first.index < second.index)
            return -1;
        else if (first.index == second.index) {
            if (first instanceof Arrow && second instanceof Arrow) {
                Arrow w = (Arrow) first;
                Arrow v = (Arrow) second;
                // 用过的排在前面
                if (w.durability < v.durability) {
                    return -1;
                } else if (w.durability == v.durability) {
                    return 0;
                } else {
                    return 1;
                }
            }
            return 0;
        }

        else
            return 1;
    }
}