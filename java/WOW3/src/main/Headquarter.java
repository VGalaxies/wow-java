package main;

import java.util.ArrayList;

public class Headquarter {
    private final String name;

    private int blood;
    private static int loyaltyDecrease;
    private static int cityNum;

    private int indexWarrior;
    private final ArrayList<Warrior> warriorList;
    private static int[] bloodWarriorList;
    private static int[] attackWarriorList;
    private static final String[] nameWarriorList = new String[]{"dragon", "ninja", "iceman", "lion", "wolf"};


    private boolean hasNext;

    private int[] order;
    private int index;

    public void setOrder(int[] order) {
        this.order = order;
    }

    public Headquarter(String name, int blood,
                       int[] bloodWarriorList, int[] attackWarriorList,
                       int loyaltyDecrease, int cityNum) {
        this.blood = blood;
        this.name = name;
        Headquarter.loyaltyDecrease = loyaltyDecrease;
        Headquarter.cityNum = cityNum;

        this.indexWarrior = 1;
        Headquarter.bloodWarriorList = bloodWarriorList;
        Headquarter.attackWarriorList = attackWarriorList;
        this.warriorList = new ArrayList<>();

        this.hasNext = true;
    }

    public void bornWarrior(int hour, int minute) {
        int nowIndex = order[index++ % 5];

        if (blood < bloodWarriorList[nowIndex]) {
            hasNext = false;
            return;
        }

        blood -= bloodWarriorList[nowIndex];
        Warrior warrior = Warrior.switchTable(name, nameWarriorList[nowIndex], indexWarrior,
                bloodWarriorList[nowIndex], attackWarriorList[nowIndex],
                (getName().equals("red") ? 0 : cityNum + 1), blood, loyaltyDecrease);
        warriorList.add(warrior);

        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(name + " ");
        mes.append(nameWarriorList[nowIndex] + " ");
        mes.append(indexWarrior++ + " ");
        mes.append("born");


        System.out.println(mes.toString());

        String bornMes = warrior.bornMessage();
        if (!bornMes.equals("")) {
            System.out.println(bornMes);
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    private String getName() {
        return name;
    }

    public ArrayList<Warrior> getWarriorList() {
        return warriorList;
    }

    public static int getCityNum() {
        return cityNum;
    }

    public static void occupied(String name, int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(name).append(" headquarter was taken");

        System.out.println(mes.toString());
    }

    public void reportElement(int hour, int minute) {
        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d:", hour));
        mes.append(String.format("%02d ", minute));
        mes.append(blood + " elements in " + name + " headquarter");

        System.out.println(mes.toString());
    }


}