import customexceptions.NoBalanceException;
import customexceptions.OperationCancelledException;

import java.util.ArrayList;

class ATM {
    private Card loggedCard;
    private final FileHandler fileHandler;

    public ATM(Card loggedCard, String userInfoPathFile) {
        this.loggedCard = loggedCard;
        this.fileHandler = new FileHandler(userInfoPathFile);
    }

    public void transfer() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        AuditFileHandler auditFileHandler = new AuditFileHandler("resources/audit");

        if (loggedCard.getAccountBalance() == 0) {
            throw new NoBalanceException();
        }

        String cardNumber = InputHelper.getCardNumber();
        if (cardNumber == null) {
            throw new OperationCancelledException();
        }

        String amountToTransfer = InputHelper.getAmountToTransfer(loggedCard);
        if (amountToTransfer == null) {
            throw new OperationCancelledException();
        }

        if (Integer.parseInt(amountToTransfer) > loggedCard.getAccountBalance() || !(fileHandler.isCardNumberValid(cardNumber, null))) {
            System.out.println("\nThere was an error when trying to complete the transaction\n");
            auditFileHandler.addAuditLog(loggedCard.getCardNumber(), cardNumber, amountToTransfer, false);
        } else {
            fileHandler.performTransfer(cardNumber, amountToTransfer, updatedResourceFile, loggedCard);
            auditFileHandler.addAuditLog(loggedCard.getCardNumber(), cardNumber, amountToTransfer, true);
            System.out.println("\nTransfer was a success!\n");
        }
    }

    public void withdrawal() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();

        if (loggedCard.getAccountBalance() == 0) {
            throw new NoBalanceException();
        }

        String amountToWithdraw = InputHelper.getAmount(loggedCard);
        if (amountToWithdraw == null) {
            throw new OperationCancelledException();
        }

        fileHandler.performWithdrawal(amountToWithdraw, updatedResourceFile, loggedCard);

        System.out.println("\nWithdrawal was a success!");
    }

    public void deposit() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        String amountToDeposit = InputHelper.getAmount(loggedCard);
        if (amountToDeposit == null) {
            throw new OperationCancelledException();
        }

        fileHandler.performDeposit(amountToDeposit, updatedResourceFile, loggedCard);

        System.out.println("\nDeposit was successful!\n");
    }

    public void blockCard() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();

        boolean doesWantTheCardBlocked = InputHelper.doesWantTheCardBlock();
        if (doesWantTheCardBlocked) {
            fileHandler.blockCard(updatedResourceFile, loggedCard);
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
