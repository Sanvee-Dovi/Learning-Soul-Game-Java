package lsg.bags;

import lsg.armor.BlackWitchVeil;
import lsg.armor.DragonSlayerLeggings;
import lsg.consumables.food.Hamburger;
import lsg.exceptions.BagFullException;
import lsg.weapons.Sword;

public class SmallBag extends bag {
  public SmallBag(){
      super(10);
  }
  public static void main(String[] args) throws BagFullException {
      SmallBag SBG = new SmallBag();
      SBG.push(new BlackWitchVeil());
      SBG.push(new Hamburger());
      SBG.push(new Sword());
      DragonSlayerLeggings Dragon = new DragonSlayerLeggings();
      SBG.push(Dragon);
    String m=  SBG.toString();
    System.out.println(m);
      SBG.pop(Dragon);

      String m2=  SBG.toString();
      System.out.println(m2);
  }
}
