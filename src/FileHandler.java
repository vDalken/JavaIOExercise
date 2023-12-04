import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class FileHandler implements UsersRepository{
    private final String FILE_PATH;

    public FileHandler(String filePath) {
        this.FILE_PATH = filePath;
    }

    public void createAccount(String name, String cardNumber, String passcode) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            String dataToWrite = name + "-" + cardNumber + "-" + passcode + "-0-false\n";
            writer.append(dataToWrite);
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to create an account: " + e.getMessage());
        }
    }

    public boolean isCardNumberValid(String cardNumber, String passcode) {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (passcode == null) {
                    if (hasCardNumber(splitData, cardNumber)) return true;
                } else {
                    if (hasCardNumberAndPasscode(splitData, cardNumber, passcode)) return true;
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to check if a card number is valid: " + e.getMessage());
        }
        return false;
    }

    public Card getCardInfo(String cardNumber) {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    return createCard(splitData);
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to get card info: " + e.getMessage());
        }
        return null;
    }

    public void updateFile(ArrayList<String> updatedResourceFile) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (String data : updatedResourceFile) {
                writer.append(data).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("An error has occurred while processing data",e);
        }
    }

    public void performTransfer(String cardNumber, String amountToTransfer, ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    handleCardNumberMatch(splitData, amountToTransfer, updatedResourceFile);
                } else if (splitData[1].equals(loggedCard.getCardNumber())) {
                    handleLoggedCardMatch(splitData, amountToTransfer, updatedResourceFile, loggedCard);
                } else {
                    updatedResourceFile.add(data);
                }
                data = bufferedReader.readLine();
            }
            updateFile(updatedResourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to update card data: " + e.getMessage());
        }
    }

    public void performWithdrawal(String amountToWithdraw, ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(loggedCard.getCardNumber())) {
                    handleCardNumberMatchWithdrawal(splitData, amountToWithdraw, updatedResourceFile);
                } else {
                    updatedResourceFile.add(data);
                }
                data = bufferedReader.readLine();
            }
            updateFile(updatedResourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to update card data: " + e.getMessage());
        }
    }

    public void performDeposit(String amountToDeposit, ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(loggedCard.getCardNumber())) {
                    handleCardNumberMatchDeposit(splitData, amountToDeposit, updatedResourceFile);
                } else {
                    updatedResourceFile.add(data);
                }
                data = bufferedReader.readLine();
            }
            updateFile(updatedResourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to update card data: " + e.getMessage());
        }
    }

    public void blockCard(ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(loggedCard.getCardNumber())) {
                    handleCardNumberMatchBlockedCard(splitData, updatedResourceFile);
                } else {
                    updatedResourceFile.add(data);
                }
                data = bufferedReader.readLine();
            }
            updateFile(updatedResourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to update card data: " + e.getMessage());
        }
    }

    private boolean hasCardNumber(String[] splitData, String cardNumber) {
        return splitData[1].equals(cardNumber);
    }

    private boolean hasCardNumberAndPasscode(String[] splitData, String cardNumber, String passcode) {
        return splitData[1].equals(cardNumber) && splitData[2].equals(passcode);
    }

    private Card createCard(String[] splitData) {
        boolean isCardBlocked = splitData[4].equals("true");
        return new Card(splitData[0], splitData[1], splitData[2], Integer.parseInt(splitData[3]), isCardBlocked);
    }

    private void handleCardNumberMatch(String[] splitData, String amountToTransfer, ArrayList<String> updatedResourceFile) {
        int amountOfTheAccountThatIsGoingToBeTransferredTheMoneyTo = Integer.parseInt(splitData[3]);
        int totalAmountOfUserThatIsGoingToReceiveMoney = Integer.parseInt(amountToTransfer) + amountOfTheAccountThatIsGoingToBeTransferredTheMoneyTo;
        splitData[3] = String.valueOf(totalAmountOfUserThatIsGoingToReceiveMoney);
        updatedResourceFile.add(concatenateSplitData(splitData));
    }

    private void handleCardNumberMatchWithdrawal(String[] splitData, String amountToWithdraw, ArrayList<String> updatedResourceFile) {
        int accountBalance = Integer.parseInt(splitData[3]);
        int newAccountBalance = accountBalance - Integer.parseInt(amountToWithdraw);
        splitData[3] = String.valueOf(newAccountBalance);
        updatedResourceFile.add(concatenateSplitData(splitData));
    }

    private void handleCardNumberMatchDeposit(String[] splitData, String amountToDeposit, ArrayList<String> updatedResourceFile) {
        int accountBalance = Integer.parseInt(splitData[3]);
        int newAccountBalance = accountBalance + Integer.parseInt(amountToDeposit);
        splitData[3] = String.valueOf(newAccountBalance);
        updatedResourceFile.add(concatenateSplitData(splitData));
    }

    private void handleCardNumberMatchBlockedCard(String[] splitData, ArrayList<String> updatedResourceFile) {
        splitData[4] = String.valueOf(true);
        updatedResourceFile.add(concatenateSplitData(splitData));
    }

    private void handleLoggedCardMatch(String[] splitData, String amountToTransfer, ArrayList<String> updatedResourceFile, Card loggedCard) {
        int newBalance = loggedCard.getAccountBalance() - Integer.parseInt(amountToTransfer);
        splitData[3] = String.valueOf(newBalance);
        updatedResourceFile.add(concatenateSplitData(splitData));
    }

    private String concatenateSplitData(String[] splitData) {
        String dataToWrite = "";
        for(String data : splitData){
            dataToWrite = dataToWrite.concat(data + "-");
        }
        dataToWrite = dataToWrite.substring(0, dataToWrite.length() - 1);
        return dataToWrite;
    }
}
