package interfaces;

public interface AccountManagement {
    void createAccount(String name, String cardNumber, String passcode);

    boolean isCardNumberValid(String cardNumber, String passcode);
}
