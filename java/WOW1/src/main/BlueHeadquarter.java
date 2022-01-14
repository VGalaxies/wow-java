package main;

public class BlueHeadquarter extends Headquarter {

    public BlueHeadquarter(int blood, int[] bloodWarriorList, String name) {
        super(blood, bloodWarriorList, name);
        // dragon、ninja、iceman、lion、wolf
        // lion、dragon、ninja、iceman、wolf
        order = new int[]{3, 0, 1, 2, 4};
    }

}
