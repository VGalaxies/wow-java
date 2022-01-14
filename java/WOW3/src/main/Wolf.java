package main;

import java.util.ArrayList;
import java.util.Collections;

public class Wolf extends Warrior {

    public Wolf(String headquarter, String name, int index, int blood, int attack, int location) {
        super(headquarter, name, index, blood, attack, location);
    }

    public String bornMessage() {
        return "";
    }

    public void steal(Warrior other, int hour, int minute) {
        ArrayList<Weapon> otherWeapons = other.weaponList;
        Collections.sort(otherWeapons, new stealOrder());
        int myWeaponSize = this.weaponList.size();
        if (otherWeapons.size() != 0) {
            int weaponIndex = otherWeapons.get(0).getIndex();
            int endIndex;
            for (endIndex = 0; ; ++endIndex) {
                if (endIndex + myWeaponSize >= 10) break;
                if (endIndex >= otherWeapons.size()) break;
                if (otherWeapons.get(endIndex).getIndex() != weaponIndex) break;
            }
            if (endIndex >= 0) {
                for (int i = 0; i < endIndex; ++i) {
                    Weapon weapon = otherWeapons.remove(0);
                    weapon.user = this;
                    this.weaponList.add(weapon);
                }
                StringBuilder mes = new StringBuilder();
                mes.append(String.format("%03d:", hour));
                mes.append(String.format("%02d ", minute));
                mes.append(headquarter + " " + name + " " + index);
                mes.append(" took " + endIndex + " " + Weapon.getNameList()[weaponIndex]);
                mes.append(" from " + other.headquarter + " " + other.name + " " + other.index);
                mes.append(" in city " + location);
                System.out.println(mes.toString());
            }
        }
    }
}
