
package lsg;

import lsg.armor.ArmorItem;
import lsg.armor.BlackWitchVeil;
import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.buffs.rings.DragonSlayerRing;
import lsg.buffs.rings.RingOfDeath;
import lsg.buffs.talismans.MoonStone;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Lycanthrope;
import lsg.characters.Monster;
import lsg.consumables.Consumable;
import lsg.consumables.MenuBestOfV4;
import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.food.Hamburger;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.weapons.ShotGun;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;
import lsg.bags.bag;
import lsg.bags.MediumBag;
import lsg.bags.SmallBag;
import lsg.bags.Collectible;

import java.util.Scanner;

public class LearningSoulsGame {

	public static final String BULLET_POINT = "\u2219 ";

	Scanner scanner = new Scanner(System.in);

	Hero hero;
	Monster monster;
	SmallBag SBG = new SmallBag();



	private void createExhaustedHero() throws WeaponNullException {
		hero = new Hero();

		// pour vider la vie
		hero.getHitWith(99);

		// pour depenser la stam

		hero.setWeapon(new Weapon("Grosse Arme", 0, 0, 1000, 100));
		//hero.setWeapon(null);
		try {
			hero.attack();
		}catch (WeaponNullException  exception){
			exception.printStackTrace();
			hero.setWeapon(null);
		}catch (WeaponBrokenException exception){
			exception.printStackTrace();
		}catch (StaminaEmptyException exception){
			exception.printStackTrace();
		}

		System.out.println(hero);
//		System.out.println(hero.getWeapon());

	}


	private void init() {
		hero = new Hero();
		hero.setWeapon(new Sword());
		hero.setArmorItem(new DragonSlayerLeggings(), 1);
		hero.setRing(new RingOfDeath(), 1);
		hero.setRing(new DragonSlayerRing(), 2);

		hero.setConsumable(new Hamburger());

		monster = new Lycanthrope(); // plus besoin de donner la skin et l'arme !
		monster.setTalisman(new MoonStone());
	}

	private void play() throws WeaponNullException, ConsumeNullException, NoBagException, BagFullException {
		init();
		fight1v1();
	}

	private void fight1v1() throws WeaponNullException, ConsumeNullException, NoBagException, BagFullException {

		refresh();

		Character agressor = hero;
		Character target = monster;
		int action; // TODO sera effectivement utilise dans une autre version
		int attack , hit;
		Character tmp;

		while (hero.isAlive() && monster.isAlive()) { // ATTENTION : boucle infinie si 0 stamina...

			action = 1; // par defaut on lancera une attaque
			System.out.println();

			if (agressor == hero) {
				do {
					System.out.print("Hero's action for next move : (1) attack | (2) consume > ");
					action = scanner.nextInt(); // GENERERA UNE ERREUR L'UTILISATEUR ENTRE AUTRE CHOSE QU'UN ENTIER (ON TRAITERA PLUS TARD)
				} while (action < 1 || action > 2);
				System.out.println();
			}

			if (action == 2) {
				try{
					hero.consume();
				}catch (ConsumeNullException exception){
					hero.printConsumable();
					System.out.println("IMPOSSIBLE ACTION :no Consumable has been equiped !");
				}catch (ConsumeEmptyException exception){
                    hero.printConsumable();
                 String MS =  String.format("ACTION HAS NO EFFECT : %1s is empty",this.hero.getConsumable().getName());
                 System.out.println(MS);
				}catch (ConsumeRepairNullWeaponException exception){
					System.out.println("IMPOSSIBLE ACTION :no Weapon  has been equiped !");
				}

				System.out.println();
			} else {

				try {
					attack = agressor.attack();
				} catch (WeaponNullException  exception) {
					attack=0;
					System.out.println("WARNING : no Weapon has been equiped");
				}catch (WeaponBrokenException exception){
					attack=0;
					String str = String.format("WARNING : %1s is Broken", hero.getWeapon().getName());
					System.out.println(str);
				}catch (StaminaEmptyException exception){
					attack =0;
					System.out.println("ACTION HAS NO EFFECT : no more stamina !!!");
				}


				hit = target.getHitWith(attack);
				System.out.printf("%s attacks %s with %s (ATTACK:%d | DMG : %d)", agressor.getName(), target.getName(), agressor.getWeapon(), attack, hit);
				System.out.println();
				System.out.println();
			}

			refresh();

			tmp = agressor;
			agressor = target;
			target = tmp;

		}

		Character winner = (hero.isAlive()) ? hero : monster;
		System.out.println();
		System.out.println("--- " + winner.getName() + " WINS !!! ---");

	}

	private void refresh() throws NoBagException, BagFullException {
		hero.printStats();
		String msg4 = hero.armorToString();
		System.out.println(msg4);
		String msg5 = hero.printRings();
		System.out.println(msg5);
		String msg = hero.printConsumable();
		System.out.println(msg);
		String msg1 = hero.printWeapon();
		System.out.println(msg1);
		String msg2 =this.hero.printbag();
		System.out.println(msg2);
		System.out.println();
		monster.printStats();
		String msg3 =monster.printWeapon();
		System.out.println(msg3);
		hero.setBag(null);

	}

	private void title() {
		System.out.println();
		System.out.println("###############################");
		System.out.println("#   THE LEARNING SOULS GAME   #");
		System.out.println("###############################");
		System.out.println();
	}

public void  testExceptions() throws WeaponNullException, ConsumeNullException, NoBagException, BagFullException {
		//hero.setWeapon(new Weapon("pelle casse",0,100,2,0));
	// hero.setWeapon(null);
	// hero.setConsumable(new RepairKit());
	  //hero.setBag(new MediumBag());
		//hero.setStamina(0);
     // hero.getConsumable().setCapacity(0);
		fight1v1();
}


	public static void main(String[] args) throws WeaponNullException, ConsumeNullException, NoBagException, BagFullException {
		LearningSoulsGame lsg = new LearningSoulsGame();
		lsg.init();
		lsg.testExceptions();
		//hero.setBag(new MediumBag());
		lsg.refresh();




	}

// 2.2  on obtient une exeption car l'arme de l'hero est nulle ce qui est impossible.


}
