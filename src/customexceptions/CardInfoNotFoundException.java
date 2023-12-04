package customexceptions;

public class CardInfoNotFoundException extends RuntimeException {
    public CardInfoNotFoundException(){
        super("Account with those details doesn't exist\nMake sure you're typing in the right card number and passcode");
    }
}
