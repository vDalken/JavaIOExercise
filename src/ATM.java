import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class ATM {
    private Card loggedCard;
    private final String USER_INFO_PATH_FILE;
    private final String AUDIT_PATH_FILE = "resources/audit";

    public ATM(Card loggedCard, String userInfoPathFile) {
        this.loggedCard = loggedCard;
        this.USER_INFO_PATH_FILE = userInfoPathFile;
    }

    public void transfer() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(USER_INFO_PATH_FILE);

        if (loggedCard.getAccountBalance() == 0) {
            System.out.println("\nYou have to make a deposit first\n");
            return;
        }

        String cardNumber = InputHandler.getCardNumber();
        if (cardNumber == null) {
            return;
        }

        String amountToTransfer = InputHandler.getAmountToTransfer(loggedCard);
        if (amountToTransfer == null) {
            return;
        }

        FileHandler fileHandler1 = new FileHandler(AUDIT_PATH_FILE);

        if (Integer.parseInt(amountToTransfer) > loggedCard.getAccountBalance() || !(fileHandler.isCardNumberValid(cardNumber, null))) {
            System.out.println("\nThere was an error when trying to complete the transaction\n");
            fileHandler1.updatedAuditFile(loggedCard.getCardNumber(), cardNumber, amountToTransfer, false);
        } else {
            fileHandler.updateTransferCardData(cardNumber, amountToTransfer, updatedResourceFile, loggedCard);
            fileHandler1.updatedAuditFile(loggedCard.getCardNumber(),cardNumber,amountToTransfer,true);
            System.out.println("\nTransfer was a success!\n");
        }
    }

    public void withdrawal() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(USER_INFO_PATH_FILE);

        if (loggedCard.getAccountBalance() == 0) {
            System.out.println("\nYou have to make a deposit first\n");
            return;
        }

        String amountToWithdraw = InputHandler.getAmount(loggedCard);
        if (amountToWithdraw == null) {
            return;
        }

        fileHandler.updateWithdrawalCardData(amountToWithdraw, updatedResourceFile, loggedCard);

        System.out.println("\nWithdrawal was a success!");
    }

    public void deposit() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(USER_INFO_PATH_FILE);

        String amountToDeposit = InputHandler.getAmount(loggedCard);
        if (amountToDeposit == null) {
            return;
        }

        fileHandler.updateDepositCardData(amountToDeposit, updatedResourceFile, loggedCard);

        System.out.println("\nDeposit was successful!\n");
    }

    public void blockCard() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(USER_INFO_PATH_FILE);

        boolean doesWantTheCardBlocked = InputHandler.doesWantTheCardBlock();
        if (doesWantTheCardBlocked) {
            fileHandler.updateBlockedCardData(updatedResourceFile, loggedCard);
            System.out.println("\nCard was blocked successfully!");
        } else {
            System.out.println("\nBecause you didn't give us a definitive answer, we didn't block your card\n");
        }
    }

    public void showCardInfo() {
        System.out.println("\nName: " + loggedCard.getName());
        System.out.println("Card Number: " + loggedCard.getCardNumber());
        System.out.println("Passcode : " + loggedCard.getPasscode());
        System.out.println("Account Balance: " + loggedCard.getAccountBalance() + "\n");
    }
}
