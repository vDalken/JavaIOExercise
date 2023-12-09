package classes;

import java.util.Random;

public class Card {
    private final String name;
    private final String cardNumber;
    private final String passcode;
    private int accountBalance;
    private boolean isCardBlocked;

    public Card(String name, String cardNumber,String passcode, int accountBalance, boolean isCardBlocked) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.passcode = passcode;
        this.accountBalance = accountBalance;
        this.isCardBlocked = isCardBlocked;
    }

    public Card(String name) {
        this.name = name;
        this.cardNumber = getRandomCardNumber();
        this.passcode = getRandomPasscode();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public String getName() {
        return name;
    }

    public String getPasscode() {
        return passcode;
    }

    private String getRandomPasscode() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000, 9999);
        return randomNumber + "";
    }

    private String getRandomCardNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000, 999999);
        return randomNumber + "";
    }
}
