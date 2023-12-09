package interfaces;

import classes.Card;

import java.util.ArrayList;

public interface TransactionOperations {
    void performTransfer(String cardNumber, String amountToTransfer, ArrayList<String> updatedResourceFile, Card loggedCard);

    void performWithdrawal(String amountToWithdraw, ArrayList<String> updatedResourceFile, Card loggedCard);

    void performDeposit(String amountToDeposit, ArrayList<String> updatedResourceFile, Card loggedCard);
}
