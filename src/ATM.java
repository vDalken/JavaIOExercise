import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class ATM {
    private Card loggedCard;
    private String pathFile;
    public ATM(Card loggedCard, String pathFile) {
        this.loggedCard=loggedCard;
        this.pathFile = pathFile;
    }

    public void transfer() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler(pathFile);

        if (loggedCard.getAccountBalance() == 0) {
            System.out.println("\nYou have to make a deposit first\n");
            return;
        }

        String cardNumber = InputHandler.getCardNumber(fileHandler);
        if (cardNumber==null){
            return;
        }

        String amountToTransfer = InputHandler.getAmountToTransfer(loggedCard);
        if(amountToTransfer==null){
            return;
        }

        fileHandler.updateCardData(cardNumber,amountToTransfer, updatedResourceFile, loggedCard);
    }
}
