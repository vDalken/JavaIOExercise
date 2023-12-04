import java.util.ArrayList;

class ATM {
    private Card loggedCard;
    private final String USER_INFO_PATH_FILE;
    private final FileHandler fileHandler;

    public ATM(Card loggedCard, String userInfoPathFile) {
        this.loggedCard = loggedCard;
        this.USER_INFO_PATH_FILE = userInfoPathFile;
        this.fileHandler = new FileHandler(USER_INFO_PATH_FILE);
    }

    public void transfer() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        AuditFileHandler auditFileHandler = new AuditFileHandler("resources/audit");

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

        if (Integer.parseInt(amountToTransfer) > loggedCard.getAccountBalance() || !(fileHandler.isCardNumberValid(cardNumber, null))) {
            System.out.println("\nThere was an error when trying to complete the transaction\n");
            auditFileHandler.updateAuditFile(loggedCard.getCardNumber(), cardNumber, amountToTransfer, false);
        } else {
            fileHandler.performTransfer(cardNumber, amountToTransfer, updatedResourceFile, loggedCard);
            auditFileHandler.updateAuditFile(loggedCard.getCardNumber(),cardNumber,amountToTransfer,true);
            System.out.println("\nTransfer was a success!\n");
        }
    }

    public void withdrawal() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();

        if (loggedCard.getAccountBalance() == 0) {
            System.out.println("\nYou have to make a deposit first\n");
            return;
        }

        String amountToWithdraw = InputHandler.getAmount(loggedCard);
        if (amountToWithdraw == null) {
            return;
        }

        fileHandler.performWithdrawal(amountToWithdraw, updatedResourceFile, loggedCard);

        System.out.println("\nWithdrawal was a success!");
    }

    public void deposit() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();

        String amountToDeposit = InputHandler.getAmount(loggedCard);
        if (amountToDeposit == null) {
            return;
        }

        fileHandler.performDeposit(amountToDeposit, updatedResourceFile, loggedCard);

        System.out.println("\nDeposit was successful!\n");
    }

    public void blockCard() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();

        boolean doesWantTheCardBlocked = InputHandler.doesWantTheCardBlock();
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
