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

    public static String getAmountToTransfer(Card loggedCard) {
        Scanner scan = new Scanner(System.in);
        String amountToTransfer;
        boolean needsToRepeat = false;
        do {
            System.out.println("\nPlease write the amount you want to transfer");
            amountToTransfer = scan.nextLine();
            amountToTransfer = amountToTransfer.trim();
            if (!isAmountInNumbers(amountToTransfer)) {
                needsToRepeat = true;
            } else {
                needsToRepeat = Integer.parseInt(amountToTransfer) > loggedCard.getAccountBalance();
            }
            if (amountToTransfer.equals("0")) {
                return null;
            }
        } while (needsToRepeat);
        return amountToTransfer;
    }

    private static boolean isAmountInNumbers(String amountToTransfer) {
        return amountToTransfer.matches("\\d+$");
    }
}
