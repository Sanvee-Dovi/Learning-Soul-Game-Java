package lsg.exceptions;
import lsg.bags.bag;
public class BagFullException  extends Exception{
    private bag Bag;
    public BagFullException(bag Bag){
        super();
    }
   public String toString(){
        String M = "is Full !";
   String M1 =  String.format("%1s",getBag().getClass().getName());
   return M1 + M;
   }

    public bag getBag() {
        return Bag;
    }
}
