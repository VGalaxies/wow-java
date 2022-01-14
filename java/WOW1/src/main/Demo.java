package main;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int blood = in.nextInt();
        in.nextLine();
        String oriBloodList = in.nextLine();
        int[] bloodList = new int[5];
        // dragon、ninja、iceman、lion、wolf
        for (int i = 0; i < 5; ++i) {
            bloodList[i] = Integer.parseInt(oriBloodList.split(" ")[i]);
        }
        Headquarter red = new RedHeadquarter(blood, bloodList, "red");
        Headquarter blue = new BlueHeadquarter(blood, bloodList, "blue");

        while (red.isHasNext() || blue.isHasNext()) {
            if (red.isHasNext())
                red.bornWarrior();
            if (blue.isHasNext())
                blue.bornWarrior();
        }
    }
}
