package main;

public class RedHeadquarter extends Headquarter {

    public RedHeadquarter(int blood, int[] bloodWarriorList, String name) {
        super(blood, bloodWarriorList, name);

        // dragon、ninja、iceman、lion、wolf
        // iceman、lion、wolf、ninja、dragon
        order = new int[]{2, 3, 4, 1, 0};
    }



}
