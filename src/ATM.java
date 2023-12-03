import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class ATM {
    private Card loggedCard;
    private final String PATH_FILE;

    public ATM(Card loggedCard, String pathFile) {
        this.loggedCard = loggedCard;
        this.PATH_FILE = pathFile;
    }

    public void transfer() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(PATH_FILE);

        if (loggedCard.getAccountBalance() == 0) {
            System.out.println("\nYou have to make a deposit first\n");
            return;
        }

        String cardNumber = InputHandler.getCardNumber(fileHandler);
        if (cardNumber == null) {
            return;
        }

        String amountToTransfer = InputHandler.getAmount(loggedCard);
        if (amountToTransfer == null) {
            return;
        }

        fileHandler.updateTransferCardData(cardNumber, amountToTransfer, updatedResourceFile, loggedCard);

        System.out.println("\nTransfer was a success!");
    }

    public void withdrawal() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(PATH_FILE);

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
        FileHandler fileHandler = new FileHandler(PATH_FILE);

        String amountToDeposit = InputHandler.getAmount(loggedCard);
        if (amountToDeposit == null) {
            return;
        }

        fileHandler.updateDepositCardData(amountToDeposit, updatedResourceFile, loggedCard);

        System.out.println("\nDeposit was successful!\n");
    }

    public void blockCard() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(PATH_FILE);

        boolean doesWantTheCardBlocked = InputHandler.doesWantTheCardBlock();
        if (doesWantTheCardBlocked) {
            fileHandler.updateBlockedCardData(updatedResourceFile,loggedCard);
            System.out.println("\nCard was blocked successfully!");
        }else{
            System.out.println("\nBecause you didn't give us a definitive answer, we didn't block your card\n");
        }
    }

    public void showCardInfo(){
        System.out.println("Name: " + loggedCard.getName());
        System.out.println("Card Number: "+ loggedCard.getCardNumber());
        System.out.println("Passcode : " + loggedCard.getPasscode());
    }
}
