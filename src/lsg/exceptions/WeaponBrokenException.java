package lsg.exceptions;


import lsg.weapons.Weapon;


public class WeaponBrokenException extends Exception {
    private Weapon weapon;
    public  WeaponBrokenException(Weapon weapon){
       this.weapon = weapon;
    }
    public String toString(){
        String MSG ="monArme is Broken";
        return MSG;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
