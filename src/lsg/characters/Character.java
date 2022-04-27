package lsg.characters;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lsg.armor.ArmorItem;
import lsg.armor.DragonSlayerLeggings;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.helper.Dice;
import lsg.weapons.Weapon;
import lsg.bags.bag;
import lsg.bags.SmallBag;
import  lsg.bags.MediumBag;
import  lsg.bags.Collectible;

import java.util.HashSet;
import java.util.Locale;

public abstract class Character  {

	public static final String LIFE_STAT_STRING = "life" ;
	public static final String STAM_STAT_STRING = "stamina" ;
	public static final String PROTECTION_STAT_STRING = "protection" ;
	public static final String BUFF_STAT_STRING = "buff" ;
	private final SimpleDoubleProperty lifeRate = new SimpleDoubleProperty();
	private  final SimpleDoubleProperty StaminaRate = new SimpleDoubleProperty();


	private static String MSG_ALIVE = "(ALIVE)" ;
	private static String MSG_DEAD = "(DEAD)" ;
	
	private String name ; // Nom du personnage
	
	private int maxLife, life ; 		// Nombre de points de vie restants
	private int maxStamina, stamina ;	// Nombre de points d'action restants
	
	private Weapon weapon ;

	private Consumable consumable ;

	protected bag Bag;




	private Dice dice101 = new Dice(101) ;
	private Object Drink;

	public Character(String name) {
		this.name = name ;
		this.Bag= new SmallBag();
	}

	public  DoubleProperty lifeRateProperty(){
		 return lifeRate;
	}

	public DoubleProperty staminaRateProperty(){
		return StaminaRate;
	}


	public Consumable getConsumable() {
		return consumable;
	}

	/**
	 * Methode qui utilise le consommable equipé par le personnage
	 */
	public void consume() throws WeaponNullException, ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
		use(consumable);
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	private void eat(Food food) throws ConsumeNullException, ConsumeEmptyException {
		if(food == null) throw  new ConsumeNullException() ;
		System.out.println(getName() + " eats " + food);
		int newLife = getLife() + food.use() ;
		newLife = (newLife < maxLife) ? newLife : maxLife ;
		setLife(newLife);
	}

	private void drink(Drink drink) throws ConsumeNullException, ConsumeEmptyException {
		if(drink == null) throw new ConsumeNullException();
		System.out.println(getName() + " drinks " + drink);
		int newStam = getStamina() + drink.use() ;
		newStam = (newStam < maxStamina) ? newStam : maxStamina ;
		setStamina(newStam);
	}

	private void repairWeaponWith(RepairKit kit) throws WeaponNullException, ConsumeNullException {
		if(weapon ==null) throw new WeaponNullException() ;
		System.out.println(getName() + " repairs " + weapon + " with " + kit);
		weapon.repairWith(kit);
	}

	public void use(Consumable consumable) throws WeaponNullException, ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
		if(consumable==null){
			throw new ConsumeNullException();
		}
		if(consumable instanceof Drink){
			drink((Drink)consumable);
		}else if(consumable instanceof Food){
			eat((Food)consumable) ;
		}else if(consumable instanceof RepairKit){
			try{
				repairWeaponWith((RepairKit)consumable);
			}catch (Exception exception){
				throw new ConsumeRepairNullWeaponException();
			}

		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxLife() {
		return maxLife;
	}
	
	protected void setMaxLife(int maxLife) {
		  	lifeRate.set((double)life/maxLife);
		  	this.maxLife = maxLife;
	}
	
	public int getLife() {
		return life;
	}
	
	protected void setLife(int life) {
		lifeRate.set((double)life/maxLife) ;
		this.life = life;
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	protected void setMaxStamina(int maxStamina) {
		StaminaRate.set((double)stamina/maxStamina);
		this.maxStamina = maxStamina;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public  void setStamina(int stamina) {
		StaminaRate.set((double)stamina/maxStamina);
		this.stamina = stamina;
	}
	
	public boolean isAlive(){
		return life > 0 ;
	}
	
	public void printStats(){
		System.out.println(this);
	}

	@Override
	public String toString() {
		
		String classe = getClass().getSimpleName() ;
		String life = String.format("%5d", getLife()) ; 
		String stam = String.format("%5d", getStamina()) ; 
		String protection = String.format(Locale.US, "%6.2f", computeProtection()) ;
		String buff = String.format(Locale.US, "%6.2f", computeBuff()) ;
		
		String msg = String.format("%-20s %-20s %s:%-10s %s:%-10s %s:%-10s %s:%-10s", "[ " + classe + " ]",
				getName(),
				LIFE_STAT_STRING.toUpperCase(),
				life,
				STAM_STAT_STRING.toUpperCase(),
				stam,
				PROTECTION_STAT_STRING.toUpperCase(),
				protection,
				BUFF_STAT_STRING.toUpperCase(),
				buff) ;
		
		String status ;
		if(isAlive()){
			status = MSG_ALIVE ;
		}else{
			status = MSG_DEAD ;
		}
		
		return msg + status ;
	}
	public int attack() throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {
		return attackWith(this.getWeapon()) ;
	}

	/**
	 * Calcule une attaque en fonction d'une arme.
	 * Le calcul dépend des statistiques de l'arme, de la stamina (restante) du personnage et des buffs eventuels
	 *
	 * @param weapon : l'arme utilisée.
	 * @return la valeur de l'attaque eventuellement buffée ; 0 si l'arme est cassée.
	 * @throws WeaponNullException
	 * @throws WeaponBrokenException
	 */
	protected int attackWith(Weapon weapon) throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {

		if(weapon == null) throw new WeaponNullException() ;
		if(weapon.isBroken()) throw new WeaponBrokenException(weapon) ;
		if(stamina == 0) throw new StaminaEmptyException() ;

		int min = weapon.getMinDamage() ;
		int max = weapon.getMaxDamage() ;
		int cost = weapon.getStamCost() ;

		int attack = 0 ;

		attack = min + Math.round((max-min) * dice101.roll() / 100.f) ;
		int stam = getStamina() ;
		if(cost <= stam){ // il y a assez de stam pour lancer l'attaque
			setStamina(getStamina()-cost);
		}else{
			attack = Math.round(attack * ((float)stam / cost)) ;
			setStamina(0);
		}

		weapon.use();

		return attack + Math.round(attack*computeBuff()/100);
	}
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	/**
	 * @return le nombre de points de buff du personnage
	 */
	protected abstract float computeBuff() ; 
	
	/**
	 * @return le nombre de points de protection du personnage
	 */
	protected abstract float computeProtection() ;
	
	/**
	 * Calcule le nombre de PV retires en tenant compte de la protection
	 * @param value : le montant des degats reçus
	 * @return le nombre de PV effectivement retires (value reduite par la protecion si assez de vie ; le reste de la vie sinon)
	 */
	public int getHitWith(int value){

		int life = getLife() ;
		int dmg ;
		float protection = computeProtection() ;
		if(protection > 100) protection = 100 ; // si la protection depasse 100, elle absorbera 100% de l'attaque
		value = Math.round(value - (value * protection / 100)) ;
		dmg = (life > value) ? value : life ;
		setLife(life-dmg);
		return dmg ;

	}
	public void pickUp(Collectible item) throws NoBagException, BagFullException {
		if(this.Bag==null){
			throw new NoBagException();
		}
		if (this.Bag.getWeight()<this.Bag.getCapacity() && item.getWeight()<=this.Bag.getCapacity()-this.Bag.getWeight()){
			this.Bag.push(item);
			String M = String.format("%1s pick Up ",getName());
			System.out.println(M + item.toString());
		}
	}

	public  Collectible pullOut(Collectible item) throws NoBagException {
		if(this.Bag==null){
			throw new NoBagException();
		}
		if(this.Bag.contains(item)){
			this.Bag.pop(item);
			String M = String.format("%1s pulls  out  ",getName());
			System.out.println(M + item.toString());
			return item;
		}
		return null;
	}

	public String printbag() throws NoBagException{
		if(this.Bag==null){
			String M =String.format("BAG: Null");
			return  M;
		}
		String m = 	this.Bag.toString();
		return m;
	}

	public int  getBagCapacity() throws NoBagException {
		if(this.Bag==null){
			throw  new NoBagException();
		}
		return  this.Bag.getCapacity();
	}
	public int getBagWeight() throws NoBagException {
		if(this.Bag==null){
			throw new NoBagException();
		}
		return this.Bag.getWeight();
	}

	public Collectible [] getBagItems() throws NoBagException {
		if(this.Bag==null){
			throw new NoBagException();
		}
	  Collectible[] colect = this.Bag.getItems();
	  return colect;
	}
	public static void transfer(bag from, bag into) throws NoBagException, BagFullException {
		for (Collectible source : from.getItems()){
			if(into.getWeight()<into.getCapacity() && source.getWeight()<=into.getCapacity() - into.getWeight()){
				into.push(from.pop(source));

			}

		}
	}
	public bag setBag(bag Bag) throws NoBagException, BagFullException {
  if(Bag==null || this.Bag==null){
	  bag temporaire = this.Bag;
	  this.Bag = Bag;
	  bag.transfer(temporaire,this.Bag);
	  String Msg = String.format("%1s  changes Smallbag for Null",getName());
	  System.out.println(Msg);
	  return this.Bag;

  }
                bag temporaire = this.Bag;
                this.Bag = Bag;
                bag.transfer(temporaire,this.Bag);
               	String Msg = String.format("%1s  changes Smallbag for MediumBag",getName());
               	System.out.println(Msg);
               return this.Bag;
		 }
	public void equip(Weapon weapon) throws NoBagException {
		if(this.Bag==null){
			throw  new NoBagException();
		}
            if(this.Bag.contains(weapon)){
            	this.setWeapon(weapon);
            	 String M =String.format("%1s pulls out ",getName());
            	 System.out.println(M + weapon.toString() +" and equips it");
            	this.Bag.pop(weapon);
			}
	}

	public void equip(Consumable consumable) throws NoBagException {
		if(this.Bag==null){
			throw  new NoBagException();
		}
		if(this.Bag.contains(consumable)){
			 this.setConsumable(consumable);
			String M =String.format("%1s pulls out ",getName());
			System.out.println(M + consumable.toString() +" and equips it");
			this.Bag.pop(consumable);
		}
	}

	private Consumable fastUsefirst(Class<? extends Consumable>Type) throws WeaponNullException, ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
		for( Collectible Cm : this.Bag.getItems()){
			if (Type.isInstance(Cm)){
			   Consumable C =(Consumable) Cm;
				this.setConsumable(C);
				this.consume();
				if (((Consumable) Cm).getCapacity()==0){
					this.Bag.pop(Cm);
				}
				return  C;
			}

		}
		return  null;

	}

	public	Drink	fastDrink() throws WeaponNullException, ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
         try{
			 Drink Dr =  (Drink) fastUsefirst(lsg.consumables.drinks.Drink.class);
		 }catch (ConsumeRepairNullWeaponException exception){

		 }
		Drink Dr =  (Drink) fastUsefirst(lsg.consumables.drinks.Drink.class);
		String M =String.format("%1s Drinks : \n %1s Drinks %1s \n %1s pulls out %1s ",getName(),getName(),Dr.toString(),getName(),Dr.toString());
		System.out.println(M);
        return Dr;
	}

	 public Food fastEat() throws WeaponNullException, ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
		try{
			Food Fd = (Food) fastUsefirst(Food.class);
		}catch (ConsumeRepairNullWeaponException exception){

		}
		 Food Fd = (Food) fastUsefirst(Food.class);
		 String M =String.format("%1s eats : \n %1s eats %1s \n %1s pulls out %1s ",getName(),getName(),Fd.toString(),getName(),Fd.toString());
		 System.out.println(M);
		 return Fd;
	 }

	 public RepairKit fastRepairKit() throws WeaponNullException, ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
		  RepairKit Rk = (RepairKit) fastUsefirst(RepairKit.class);
		 String M =String.format("%1s repair : \n %1s repair %1s \n %1s pulls out %1s ",getName(),getName(),Rk.toString(),getName(),Rk.toString());
		 System.out.println(M);
		  return Rk;
	 }



	}





	

