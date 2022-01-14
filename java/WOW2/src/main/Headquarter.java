package main;

import java.util.ArrayList;

public class Headquarter {
    private String name;

    private int blood;
    private int time;

    private int indexWarrior;
    private int[] numWarriorList;
    private ArrayList<Warrior> warriorList;
    private static int[] bloodWarriorList;
    private static String[] nameWarriorList = new String[]{"dragon", "ninja", "iceman", "lion", "wolf"};


    private boolean hasNext;

    protected int[] order;
    private int index;

    public Headquarter(int blood, int[] bloodWarriorList, String name) {
        this.blood = blood;
        this.name = name;

        this.indexWarrior = 1;
        this.numWarriorList = new int[]{0, 0, 0, 0, 0};
        this.bloodWarriorList = bloodWarriorList;
        this.warriorList = new ArrayList<>();

        this.time = 0;
        this.hasNext = true;
    }

    public void bornWarrior() {
        int nowIndex = order[index++ % 5];
        int count = 0;
        while (bloodWarriorList[nowIndex] > blood) {
            count++;
            nowIndex = order[index++ % 5];
            if (count > 5) {
                StringBuilder builder = new StringBuilder();
                builder.append(String.format("%03d ", time));
                builder.append(name + " ");
                builder.append("headquarter stops making warriors");
                System.out.println(builder.toString());

                hasNext = false;
                return;
            }
        }

        blood -= bloodWarriorList[nowIndex];
        Warrior warrior = Warrior.switchTable(nameWarriorList[nowIndex], bloodWarriorList[nowIndex], indexWarrior, blood);
        warriorList.add(warrior);

        StringBuilder mes = new StringBuilder();
        mes.append(String.format("%03d ", time++));
        mes.append(name + " ");
        mes.append(nameWarriorList[nowIndex] + " ");
        mes.append(indexWarrior++ + " ");
        mes.append("born with strength ");
        mes.append(bloodWarriorList[nowIndex] + ",");
        mes.append(++numWarriorList[nowIndex] + " ");
        mes.append(nameWarriorList[nowIndex] + " " + "in ");
        mes.append(name + " headquarter");

        System.out.println(mes.toString());

        String bornMes = warrior.bornMessage();
        if (!bornMes.equals("")) {
            System.out.println(bornMes);
        }
    }

    public boolean hasNext() {
        return hasNext;
    }
}