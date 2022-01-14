package main;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] info = in.nextLine().split(" ");
        int blood = Integer.parseInt(info[0]);
        int cityNum = Integer.parseInt(info[1]);
        int loyaltyDecrease = Integer.parseInt(info[2]);
        int timeLimit = Integer.parseInt(info[3]);

        String oriBloodList = in.nextLine();
        int[] bloodList = new int[5];
        // dragon、ninja、iceman、lion、wolf
        for (int i = 0; i < 5; ++i) {
            bloodList[i] = Integer.parseInt(oriBloodList.split(" ")[i]);
        }

        String oriAttackList = in.nextLine();
        int[] attackList = new int[5];
        // dragon、ninja、iceman、lion、wolf
        for (int i = 0; i < 5; ++i) {
            attackList[i] = Integer.parseInt(oriAttackList.split(" ")[i]);
        }

        Headquarter red = new Headquarter("red", blood, bloodList, attackList, loyaltyDecrease, cityNum);
        red.setOrder(new int[]{2, 3, 4, 1, 0});
        Headquarter blue = new Headquarter("blue", blood, bloodList, attackList, loyaltyDecrease, cityNum);
        blue.setOrder(new int[]{3, 0, 1, 2, 4});

        Event event = new Event(red, blue, timeLimit);
        event.start();
    }
}
