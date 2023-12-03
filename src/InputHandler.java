import java.util.Scanner;

class InputHandler {
    public static String getCardNumber(FileHandler fileHandler) {
        Scanner scan = new Scanner(System.in);
        boolean isCardNumberValid = false;
        String cardNumber = "";
        while (!isCardNumberValid) {
            System.out.println("\nPlease write the card number");
            cardNumber = scan.nextLine();
            cardNumber = cardNumber.trim();
            if ((fileHandler.isCardNumberValid(cardNumber,null))) {
                isCardNumberValid = true;
            }
            if (cardNumber.equals("0")) {
                return null;
            }
        }
        return cardNumber;
    }

    public static String getAmount(Card loggedCard) {
        Scanner scan = new Scanner(System.in);
        String amount;
        boolean needsToRepeat;
        do {
            System.out.println("\nPlease write the amount");
            amount = scan.nextLine();
            amount = amount.trim();
            if (!isAmountInNumbers(amount)) {
                needsToRepeat = true;
            } else {
                needsToRepeat = Integer.parseInt(amount) > loggedCard.getAccountBalance();
            }
            if (amount.equals("0")) {
                return null;
            }
        } while (needsToRepeat);
        return amount;
    }

    public static boolean doesWantTheCardBlock(){
        Scanner scan = new Scanner(System.in);
        System.out.println("\nYou wont be able to login anymore and you'll have to get a new one");
        System.out.println("\nDo you want your card blocked?");
        return scan.nextBoolean();
    }

    private static boolean isAmountInNumbers(String amountToTransfer) {
        return amountToTransfer.matches("\\d+$");
    }
}
