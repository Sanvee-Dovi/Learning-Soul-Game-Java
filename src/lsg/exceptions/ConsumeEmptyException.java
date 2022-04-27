package lsg.exceptions;

public class ConsumeEmptyException extends Exception {
    public ConsumeEmptyException(){
            super("Consumable is empty");
    }
}
