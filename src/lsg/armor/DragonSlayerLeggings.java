package lsg.armor;

import lsg.bags.Collectible;

public class DragonSlayerLeggings extends ArmorItem implements Collectible {

	private static String ARMOR_NAME = "Dragon Slayer Leggings" ;
	private static float ARMOR_VALUE = 10.2f ; 
	
	public DragonSlayerLeggings() {
		super(ARMOR_NAME, ARMOR_VALUE) ;
	}

	@Override
	public int getWeight() {
		return 3;
	}
}
