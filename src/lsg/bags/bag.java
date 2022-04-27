package lsg.bags;

import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.exceptions.BagFullException;
import lsg.weapons.ShotGun;

import java.util.HashSet;

public class bag {
    private int capacity;
    private int weight;
    private HashSet<Collectible> items = new HashSet<>();

    public bag(final int cap) {
        this.capacity = cap;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWeight() {
        return weight;
    }

    public void push(Collectible item) throws BagFullException {

        if (this.weight < this.capacity && item.getWeight()<=this.capacity-this.weight) {
            this.items.add(item);
            this.weight += item.getWeight();

        }
        else{
           throw new BagFullException(this);
        }
    }

    public Collectible pop(Collectible item) {

        if (this.items.contains(item)) {
            this.items.remove(item);
            this.weight-=item.getWeight();
            return item;
        }
        return null;
    }

    public boolean contains(Collectible item) {
        for (Collectible it : items) {
            if (it == item) {
                return true;
            }
        }
        return false;
    }

    public Collectible [] getItems(){
         Collectible [] Col = new Collectible[items.size()];
     int i=0;
             for(Collectible it : items){
                 Col[i]= it;
                 i++;
         }
         return Col;

    }


    public  String toString(){

        String nomclassebag = getClass().getSimpleName();
        String Nbitems = String.format("%1d", this.items.size());
        String CapaciteMax = String.format("%1d",this.capacity);
        String Poids =String.format("%1d",getWeight());

        String msg = String.format("%1s [ %1s items | %1s/%1s kg ]",nomclassebag,Nbitems,Poids,CapaciteMax);

        if(this.items.isEmpty()){
            return msg +"\n •"+"(empty)";
        }

        for(Collectible it : items){
            msg += ("\n •"+ it.toString() + "["+ it.getWeight() + "kg"+ "]");
        }
        return msg;
    }
    public static void transfer(bag from, bag into) throws BagFullException {
        if(into ==null || from==null ){
            return;
        }
        if(into.getCapacity() == into.getWeight()){
            throw new BagFullException(into);
        }
        for (Collectible source : from.getItems()){
                  if(into.weight<into.capacity && source.getWeight()<=into.capacity - into.weight){
                    into.push(from.pop(source));

            }

        }
    }
    public static void main(String[] args) throws BagFullException {
        bag source = new bag(10);
        bag destination = new bag(5);
        source.push(new ShotGun());
        source.push(new DragonSlayerLeggings());
        source.push(new RingedKnightArmor());
        String s = source.toString();
        String d= destination.toString();
        System.out.println(s);
        System.out.println(d);
        transfer(source,destination);
        String i=destination.toString();
        String j= source.toString();
        System.out.println(i);
        System.out.println(j);
    }



}