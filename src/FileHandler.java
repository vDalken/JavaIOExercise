import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public void writeOnTheFile(String name, String cardNumber, String passcode) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String dataToWrite = name + "-" + cardNumber + "-" + passcode + "-0-false\n";
            writer.append(dataToWrite);
        } catch (IOException e) {
            System.out.println("Error writing on the file: " + e.getMessage());
        }
    }

    public boolean isCardNumberValid(String cardNumber, String passcode) {
        try (FileReader reader = new FileReader(filePath)) {
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
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean hasCardNumber(String[] splitData, String cardNumber) {
        return splitData[1].equals(cardNumber);
    }

    private boolean hasCardNumberAndPasscode(String[] splitData, String cardNumber, String passcode) {
        return splitData[1].equals(cardNumber) && splitData[2].equals(passcode);
    }

    public Card getCardInfo(String cardNumber) {
        try (FileReader reader = new FileReader(filePath)) {
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
            throw new RuntimeException(e);
        }
        return null;
    }

    private Card createCard(String[] splitData) {
        boolean isCardBlocked = splitData[4].equals("true");
        return new Card(splitData[0], splitData[1], splitData[2], Integer.parseInt(splitData[3]), isCardBlocked);
    }

    public void updateFile(ArrayList<String> updatedResourceFile) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String data : updatedResourceFile) {
                writer.append(data).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTransferCardData(String cardNumber, String amountToTransfer, ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(filePath)) {
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

    private void handleCardNumberMatch(String[] splitData, String amountToTransfer, ArrayList<String> updatedResourceFile) {
        int amountOfTheAccountThatsGoingToBeTransferredTheMoneyTo = Integer.parseInt(splitData[3]);
        int totalAmountOfUserThatIsGoingToReceiveMoney = Integer.parseInt(amountToTransfer) + amountOfTheAccountThatsGoingToBeTransferredTheMoneyTo;
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
        for (int i = 0; i < splitData.length; i++) {
            dataToWrite = dataToWrite.concat(splitData[i] + "-");
        }
        dataToWrite = dataToWrite.substring(0, dataToWrite.length() - 1);
        return dataToWrite;
    }

    public void updateWithdrawalCardData(String amountToWithdraw, ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(filePath)) {
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

    public void updateDepositCardData(String amountToDeposit, ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(filePath)) {
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

    public void updateBlockedCardData(ArrayList<String> updatedResourceFile, Card loggedCard) {
        try (FileReader reader = new FileReader(filePath)) {
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

    public void updatedAuditFile(String loggedCardNumber,String receiverCardNumber, String amountToTransfer, boolean isSucessful) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String status = isSucessful ? "success" : "error";
            String dataToWrite = loggedCardNumber+ "-" + receiverCardNumber + "-" + amountToTransfer + "-" + status + "\n";
            writer.append(dataToWrite);
        } catch (IOException e) {
            System.out.println("Error on updating the file: " + e.getMessage());
        }
    }
}
