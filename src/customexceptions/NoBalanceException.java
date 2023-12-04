package customexceptions;

public class NoBalanceException extends RuntimeException{
    public NoBalanceException(){
        super("No money in the account. You have to make a deposit first");
    }
}
