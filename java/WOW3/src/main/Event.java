package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Event {
    private Headquarter red;
    private Headquarter blue;
    private int timeLimit;
    private int hour;
    private boolean isEnd = false;

    public Event(Headquarter red, Headquarter blue, int timeLimit) {
        this.red = red;
        this.blue = blue;
        this.timeLimit = timeLimit;
        this.hour = 0;
    }

    public void start() {
        while (true) {
            // 司令部制造武士
            if (check(0)) makeWarrior();
            else break;

            // lion 逃跑
            if (check(5)) escape();
            else break;

            // 武士前进
            if (check(10)) march();
            else break;

            // wolf 抢武器
            if (check(35)) steal();
            else break;

            // 战斗
            if (check(40)) battle();
            else break;

            // 司令部报告生命元数量
            if (check(50)) reportElement();
            else break;

            // 武士报告情况
            if (check(55)) reportSituation();
            else break;

            hour++;
        }

    }

    private boolean check(int minute) {
        return (hour * 60 + minute <= timeLimit) && !isEnd;
    }

    private void makeWarrior() {
        if (red.hasNext())
            red.bornWarrior(hour, 0);
        if (blue.hasNext())
            blue.bornWarrior(hour, 0);
    }

    private void escape() {
        Iterator<Warrior> warriorIterator = red.getWarriorList().listIterator();
        while (warriorIterator.hasNext()) {
            Warrior warrior = warriorIterator.next();
            if (warrior instanceof Lion) {
                Lion lion = (Lion) warrior;
                if (lion.getLoyalty() <= 0 && lion.getLocation() != Headquarter.getCityNum() + 1) {
                    lion.escape(hour, 5);
                    warriorIterator.remove();
                }
            }
        }
        warriorIterator = blue.getWarriorList().listIterator();
        while (warriorIterator.hasNext()) {
            Warrior warrior = warriorIterator.next();
            if (warrior instanceof Lion) {
                Lion lion = (Lion) warrior;
                if (lion.getLoyalty() <= 0 && lion.getLocation() != 0) {
                    lion.escape(hour, 5);
                    warriorIterator.remove();
                }
            }
        }
    }

    private void march() {
        ArrayList<Warrior> all = new ArrayList<>();
        all.addAll(red.getWarriorList());
        all.addAll(blue.getWarriorList());
        Collections.sort(all, new marchOrder());
        for (Warrior warrior : all) {
            if (warrior.headquarter.equals("red") && warrior.location == Headquarter.getCityNum() + 1
                    || warrior.headquarter.equals("blue") && warrior.location == 0) {
                continue;
            } else {
                State state = warrior.march(hour, 10);
                if (state == State.RED || state == State.BLUE) {
                    isEnd = true;
                }
            }
        }
    }

    private void steal() {
        ArrayList<Warrior> all = new ArrayList<>();
        all.addAll(red.getWarriorList());
        all.addAll(blue.getWarriorList());
        Collections.sort(all);

        int warriorSize = all.size();

        for (int i = 0; i < warriorSize - 1; ++i) {
            Warrior u, v;
            u = all.get(i);
            v = all.get(i + 1);

            if (u.location == v.location && !u.headquarter.equals(v.headquarter)) {
                if (u instanceof Wolf && !(v instanceof Wolf)) {
                    ((Wolf) u).steal(v, hour, 35);
                } else if (!(u instanceof Wolf) && v instanceof Wolf) {
                    ((Wolf) v).steal(u, hour, 35);
                } else {
                    continue;
                }
            }
        }
    }

    private void battle() {
        ArrayList<Warrior> all = new ArrayList<>();
        all.addAll(red.getWarriorList());
        all.addAll(blue.getWarriorList());
        Collections.sort(all);

        int warriorSize = all.size();

        for (int i = 0; i < warriorSize - 1; ++i) {
            Warrior u, v;
            u = all.get(i);
            v = all.get(i + 1);

            if (!u.isDead() && !v.isDead() &&
                    u.location == v.location && !u.headquarter.equals(v.headquarter)
                    && u.location != Headquarter.getCityNum() + 1 && u.location != 0) {
                Warrior redWarrior = (u.headquarter.equals("red")) ? u : v;
                Warrior blueWarrior = (u.headquarter.equals("blue")) ? u : v;

                if (u.location % 2 == 0) {
                    blueWarrior.attack(redWarrior, hour, 40);
                } else {
                    redWarrior.attack(blueWarrior, hour, 40);
                }

            }
        }

        Iterator<Warrior> warriorIterator = red.getWarriorList().listIterator();
        while (warriorIterator.hasNext()) {
            Warrior warrior = warriorIterator.next();
            if (warrior.isDead()) {
                warriorIterator.remove();
            }
        }

        warriorIterator = blue.getWarriorList().listIterator();
        while (warriorIterator.hasNext()) {
            Warrior warrior = warriorIterator.next();
            if (warrior.isDead()) {
                warriorIterator.remove();
            }
        }

    }

    private void reportElement() {
        red.reportElement(hour, 50);
        blue.reportElement(hour, 50);
    }

    private void reportSituation() {
        ArrayList<Warrior> all = new ArrayList<>();
        all.addAll(red.getWarriorList());
        all.addAll(blue.getWarriorList());
        Collections.sort(all);
        for (Warrior warrior : all) {
            warrior.reportSituation(hour, 55);
        }
    }

}
