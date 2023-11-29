class Card {
    private final String name;
    private final String cardNumber;
    private String passcode;
    private int accountBalance;
    private boolean isCardBlocked;

    public Card(String name, String cardNumber,String passcode, int accountBalance, boolean isCardBlocked) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.passcode = passcode;
        this.accountBalance = accountBalance;
        this.isCardBlocked = isCardBlocked;
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
}
