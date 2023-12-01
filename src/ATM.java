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
        boolean isCardNumberValid = false;
        if (loggedCard.getAccountBalance() == 0) {
            System.out.println("\nYou have to make a deposit first\n");
            return;
        }
        String cardNumber = "";
        while (!isCardNumberValid) {
            System.out.println("\nPlease write the card number");
            cardNumber = scan.nextLine();
            cardNumber = cardNumber.trim();
            if ((fileHandler.isCardNumberValid(cardNumber))) {
                isCardNumberValid = true;
            }
            if(cardNumber.equals("0")){
                return;
            }
        }
        String amountToTransfer;
        boolean needsToRepeat = false;
        do {
            System.out.println("\nPlease write the amount you want to transfer");
            amountToTransfer = scan.nextLine();
            amountToTransfer=amountToTransfer.trim();
            if (!isAmountInNumbers(amountToTransfer)) {
                needsToRepeat = true;
            } else {
                needsToRepeat = Integer.parseInt(amountToTransfer) > loggedCard.getAccountBalance();
            }
            if(amountToTransfer.equals("0")){
                return;
            }
        } while (needsToRepeat);

        int amountOfTheAccountThatsGoingToBeTransferedTheMoneyTo;
        int totalAmountOfUserThatIsGoingToReceiveMoney;
        try {
            FileReader reader = new FileReader(pathFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    amountOfTheAccountThatsGoingToBeTransferedTheMoneyTo = Integer.parseInt(splitData[3]);
                    totalAmountOfUserThatIsGoingToReceiveMoney = Integer.parseInt(amountToTransfer) + amountOfTheAccountThatsGoingToBeTransferedTheMoneyTo;
                    splitData[3] = totalAmountOfUserThatIsGoingToReceiveMoney + "";
                    updatedResourceFile.add(concatenateSplitData(splitData));
                } else if (splitData[1].equals(loggedCard.getCardNumber())) {
                    int newBalance = loggedCard.getAccountBalance() - Integer.parseInt(amountToTransfer);
                    splitData[3] = newBalance + "";
                    updatedResourceFile.add(concatenateSplitData(splitData));
                } else {
                    updatedResourceFile.add(data);
                }
                data = bufferedReader.readLine();
            }
            fileHandler.updateFile(updatedResourceFile);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAmountInNumbers(String amountToTransfer) {
        return amountToTransfer.matches("\\d+$");
    }

    private String concatenateSplitData(String[] splitData) {
        String dataToWrite = "";
        for (int i = 0; i < splitData.length; i++) {
            dataToWrite = dataToWrite.concat(splitData[i] + "-");
        }
        dataToWrite = dataToWrite.substring(0, dataToWrite.length() - 1);
        return dataToWrite;
    }
}
