package main;

import java.util.*;

public abstract class Warrior implements Comparable<Warrior> {
    protected String headquarter;
    protected String name;
    protected int blood;
    protected int attack;
    protected int location;
    protected int index;
    protected ArrayList<Weapon> weaponList;

    public static Warrior switchTable(String headquarter, String name, int index, int blood, int attack, int location,
                                      int bloodHeadquarter,
                                      int loyaltyDecrease) {
        switch (name) {
            case "dragon":
                return new Dragon(headquarter, name, index, blood, attack, location, bloodHeadquarter);
            case "ninja":
                return new Ninja(headquarter, name, index, blood, attack, location);
            case "iceman":
                return new Iceman(headquarter, name, index, blood, attack, location);
            case "lion":
                return new Lion(headquarter, name, index, blood, attack, location, bloodHeadquarter, loyaltyDecrease);
            case "wolf":
                return new Wolf(headquarter, name, index, blood, attack, location);
            default:
                throw new NoSuchElementException("Unknown warrior name");
        }
    }


    public Warrior(String headquarter, String name, int index, int blood, int attack, int location) {
        this.headquarter = headquarter;
        this.name = name;
        this.index = index;
        this.blood = blood;
        this.attack = attack;
        this.location = location;
        this.weaponList = new ArrayList<>();
    }

    public abstract String bornMessage();

    public int compareTo(Warrior other) {
        if (this.location < other.location)
            return -1;
        else if (this.location == other.location) {
            if (this.headquarter.equals("red") && other.headquarter.equals("blue")) {
                return -1;
            } else if (this.headquarter.equals("blue") && other.headquarter.equals("red")) {
                return 1;
            }
            return 0;

        } else
            return 1;
    }

    public int getLocation() {
        return location;
    }

    public boolean isDead() {
        return blood == 0;
    }

    public State march(int hour, int minute) {
        State flag = State.ONGOING;

        if (this instanceof Lion) {
            Lion lion = (Lion) this;
            lion.setLoyalty(lion.getLoyalty() - lion.getLoyaltyDecrease());
        }

        if (this instanceof Iceman) {
            int bloodDecrease = (int) Math.floor(this.blood * 0.1);
            this.blood -= bloodDecrease;
        }

        if (headquarter.equals("red")) location++;
        else location--;

        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(headquarter + " " + name + " " + index);
        if (headquarter.equals("red") && location == Headquarter.getCityNum() + 1) {
            mes.append(" reached blue headquarter");
            flag = State.RED;
        } else if (headquarter.equals("blue") && location == 0) {
            mes.append(" reached red headquarter");
            flag = State.BLUE;
        } else {
            mes.append(" marched to city " + location);
        }
        mes.append(" with " + blood + " elements and force " + attack);

        System.out.println(mes);

        if (flag == State.RED) {
            Headquarter.occupied("blue", hour, minute);
        } else if (flag == State.BLUE) {
            Headquarter.occupied("red", hour, minute);
        }

        return flag;
    }

    // this 先手
    public void attack(Warrior other, int hour, int minute) {
        Warrior red = (this.headquarter.equals("red")) ? this : other;
        Warrior blue = (this.headquarter.equals("blue")) ? this : other;

        ArrayList<Weapon> myWeapons = this.weaponList;
        ArrayList<Weapon> otherWeapons = other.weaponList;

        Collections.sort(myWeapons, new battleOrder());
        Collections.sort(otherWeapons, new battleOrder());

        int myWeaponIndex = 0;
        int otherWeaponIndex = 0;
        int myWeaponSize = this.checkWeaponsAndReturnNumber();
        int otherWeaponSize = other.checkWeaponsAndReturnNumber();

        State flag = isBattleEndAndReport(red, blue, hour, minute);
        int attackTime = 0;

        while (flag == State.ONGOING && attackTime <= 1000) {

            if (myWeaponIndex < myWeaponSize) {
                this.weaponList.get(myWeaponIndex++).attack(other);
                myWeaponSize = this.checkWeaponsAndReturnNumber();
            } else {
                myWeaponIndex = 0;
                myWeaponSize = this.checkWeaponsAndReturnNumber();
                if (myWeaponSize > 0) {
                    this.weaponList.get(myWeaponIndex++).attack(other);
                    myWeaponSize = this.checkWeaponsAndReturnNumber();
                }
            }

            flag = isBattleEndAndReport(red, blue, hour, minute);
            if (flag != State.ONGOING) return;

            if (otherWeaponIndex < otherWeaponSize) {
                other.weaponList.get(otherWeaponIndex++).attack(this);
                otherWeaponSize = other.checkWeaponsAndReturnNumber();
            } else {
                otherWeaponIndex = 0;
                otherWeaponSize = other.checkWeaponsAndReturnNumber();
                if (otherWeaponSize > 0) {
                    other.weaponList.get(otherWeaponIndex++).attack(this);
                    otherWeaponSize = other.checkWeaponsAndReturnNumber();
                }
            }

            flag = isBattleEndAndReport(red, blue, hour, minute);
            if (flag != State.ONGOING) return;

            attackTime++;
        }

        if (attackTime > 1000) {
            drawAlive(red, blue, hour, minute);
            return;
        }
    }

    private int checkWeaponsAndReturnNumber() {
        Iterator<Weapon> weaponIterator = weaponList.iterator();

        while (weaponIterator.hasNext()) {
            Weapon weapon = weaponIterator.next();
            if (weapon instanceof Bomb) {
                if (((Bomb) weapon).durability == 0) {
                    weaponIterator.remove();
                }
            } else if (weapon instanceof Arrow) {
                if (((Arrow) weapon).durability == 0) {
                    weaponIterator.remove();
                }
            }
        }
        return weaponList.size();
    }

    private State isBattleEndAndReport(Warrior red, Warrior blue, int hour, int minute) {
        if (red.isDead() && blue.isDead()) {
            drawDead(red, blue, hour, minute);
            return State.DRAW;
        } else if (blue.isDead()) {
            red.getWeapon(blue);
            winRed(red, blue, hour, minute);
            return State.RED;
        } else if (red.isDead()) {
            blue.getWeapon(red);
            winBlue(red, blue, hour, minute);
            return State.BLUE;
        } else if (red.weaponList.size() == 0 && blue.weaponList.size() == 0) {
            drawAlive(red, blue, hour, minute);
            return State.DRAW;
        }

        return State.ONGOING;
    }

    private void drawAlive(Warrior red, Warrior blue, int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append("both ").append(red.headquarter + " " + red.name + " " + red.index);
        mes.append(" and ").append(blue.headquarter + " " + blue.name + " " + blue.index);
        mes.append(" were alive in city ").append(red.location);

        System.out.println(mes.toString());

        if (red instanceof Dragon) {
            ((Dragon) red).yell(hour, minute);
        }

        if (blue instanceof Dragon) {
            ((Dragon) blue).yell(hour, minute);
        }
    }

    private void drawDead(Warrior red, Warrior blue, int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append("both ").append(red.headquarter + " " + red.name + " " + red.index);
        mes.append(" and ").append(blue.headquarter + " " + blue.name + " " + blue.index);
        mes.append(" died in city ").append(red.location);

        System.out.println(mes.toString());
    }

    private void winRed(Warrior red, Warrior blue, int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(red.headquarter + " " + red.name + " " + red.index);
        mes.append(" killed ");
        mes.append(blue.headquarter + " " + blue.name + " " + blue.index);
        mes.append(" in city ").append(red.location).append(" remaining ");
        mes.append(red.blood).append(" elements");

        System.out.println(mes.toString());

        if (red instanceof Dragon) {
            ((Dragon) red).yell(hour, minute);
        }
    }

    private void winBlue(Warrior red, Warrior blue, int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(blue.headquarter + " " + blue.name + " " + blue.index);
        mes.append(" killed ");
        mes.append(red.headquarter + " " + red.name + " " + red.index);
        mes.append(" in city ").append(red.location).append(" remaining ");
        mes.append(blue.blood).append(" elements");

        System.out.println(mes.toString());

        if (blue instanceof Dragon) {
            ((Dragon) blue).yell(hour, minute);
        }
    }

    public void getWeapon(Warrior other) {
        ArrayList<Weapon> otherWeapons = other.weaponList;
        Collections.sort(otherWeapons, new stealOrder());
        int myWeaponSize = this.weaponList.size();
        if (otherWeapons.size() != 0) {
            int endIndex;
            for (endIndex = 0; ; ++endIndex) {
                if (endIndex + myWeaponSize >= 10) break;
                if (endIndex >= otherWeapons.size()) break;
            }
            if (endIndex >= 0) {
                for (int i = 0; i < endIndex; ++i) {
                    Weapon weapon = otherWeapons.remove(0);
                    weapon.user = this;
                    this.weaponList.add(weapon);
                }
            }
        }
    }

    public void reportSituation(int hour, int minute) {
        ArrayList<Weapon> weapons = weaponList;
        int swordNum = 0, bombNum = 0, arrowNum = 0;
        for (Weapon weapon : weapons) {
            switch (weapon.index) {
                case 0:
                    swordNum++;
                    break;
                case 1:
                    bombNum++;
                    break;
                case 2:
                    arrowNum++;
                    break;
                default:
                    throw new NoSuchElementException("Unknown weapon name");
            }
        }

        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(headquarter + " " + name + " " + index);
        mes.append(" has ");
        mes.append(swordNum + " sword ");
        mes.append(bombNum + " bomb ");
        mes.append(arrowNum + " arrow ");
        mes.append("and ").append(blood).append(" elements");

        System.out.println(mes.toString());
    }
}

class marchOrder implements Comparator<Warrior> {
    private boolean helper(Warrior warrior) {
        return warrior.headquarter.equals("red");
    }

    public int compare(Warrior first, Warrior second) {
        int firstLocation = first.location + (helper(first) ? 1 : -1);
        int secondLocation = second.location + (helper(second) ? 1 : -1);
        if (firstLocation < secondLocation)
            return -1;
        else if (firstLocation == secondLocation) {
            if (first.headquarter.equals("red") && second.headquarter.equals("blue")) {
                return -1;
            } else if (first.headquarter.equals("blue") && second.headquarter.equals("red")) {
                return 1;
            }
            return 0;

        } else
            return 1;
    }
}
