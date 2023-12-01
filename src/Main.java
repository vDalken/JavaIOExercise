import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    static File resourceFile = new File("resources/text");
    static Card loggedCard;

    public static void main(String[] args) {
        // OPEN AN INPUT STREAM WITH A FILE PATH AS THE SOURCE
        Scanner scan = new Scanner(System.in);
        String selectedOption;
        String cardNumber;
        String passcode;
        String name = "";
        do {
            System.out.println("0. exit\n1. create account\n2. log in");
            selectedOption = scan.nextLine();
            switch (selectedOption) {
                case "0":
                    break;
                case "1":
                    System.out.println("write your name");
                    name = scan.nextLine();
                    cardNumber = getRandomCardNumber();
                    passcode = getRandomPasscode();
                    writeOnTheFile(name, cardNumber, passcode);
                    break;
                case "2":
                    System.out.println("write your card number");
                    cardNumber = scan.nextLine();
                    System.out.println();
                    System.out.println("write your passcode");
                    passcode = scan.nextLine();
                    System.out.println();
                    ArrayList<String> info = new ArrayList<>();
                    if (isCardNumberValid(cardNumber, passcode)) {
                        info = getCardInfo(cardNumber);
                        boolean isCardBlocked = info.get(4).equals("true");
                        loggedCard = new Card(info.get(0), cardNumber, info.get(2), Integer.parseInt(info.get(3)), isCardBlocked);
                        System.out.println("Welcome " + info.get(0) + "\n");
                    } else {
                        System.out.println("Account doesn't exist");
                    }
                    if (loggedCard != null) {
                        showLoggedMenu();
                    }
                    break;
                default:
                    System.out.println("\nPlease, type in a valid option\n");
                    break;
            }
        } while (!selectedOption.equals("0"));

    }

    public static String getRandomPasscode() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000, 9999);
        return randomNumber + "";
    }

    public static String getRandomCardNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000, 999999);
        return randomNumber + "";
    }

    public static void writeOnTheFile(String name, String cardNumber, String passcode) {
        try {
            FileWriter writer = new FileWriter(resourceFile.getPath(), true);
            String dataToWrite = name + "-" + cardNumber + "-" + passcode + "-0-false\n";
            writer.append(dataToWrite);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean isCardNumberValid(String cardNumber, String passcode) {
        FileReader reader = null;
        try {
            reader = new FileReader(resourceFile.getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber) && splitData[2].equals(passcode)) {
                    return true;
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static boolean isCardNumberValid(String cardNumber) {
        FileReader reader = null;
        try {
            reader = new FileReader(resourceFile.getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    return true;
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static boolean isCardNumberInTheFile(String[] splitData, String cardNumber) {
        if (splitData[1].equals(cardNumber)) {
            return true;
        }
        return false;
    }

    private static ArrayList<String> getCardInfo(String cardNumber) {
        FileReader reader;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            reader = new FileReader(resourceFile.getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    arrayList.add(splitData[0]);
                    arrayList.add(splitData[1]);
                    arrayList.add(splitData[2]);
                    arrayList.add(splitData[3]);
                    arrayList.add(splitData[4]);
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    public static void showLoggedMenu() {
        String selectedOption;
        do {
            System.out.println("0. Go Back\n1. Transfer\n2. Withdrawal\n3. Deposit\n4. Block Card");
            Scanner scan = new Scanner(System.in);
            selectedOption = scan.nextLine();
            switch (selectedOption) {
                case "0":
                    System.out.println("You're going back to the main menu");
                    break;
                case "1":
                    transfer();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                default:
                    System.out.println("You typed something that isn't valid as an option");
                    break;
            }
        } while (!selectedOption.equals("0"));
    }

    private static void updateFile(ArrayList<String> updatedResourceFile) {
        try {
            FileWriter rest = new FileWriter(resourceFile.getPath());
            rest.write("");
            rest.close();
            FileWriter writer = new FileWriter(resourceFile.getPath(), true);
            for (int i = 0; i < updatedResourceFile.size(); i++) {
                writer.append(updatedResourceFile.get(i)).append("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void transfer() {
        ArrayList<String> updatedResourceFile = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
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
            if (isCardNumberValid(cardNumber)) {
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
            FileReader reader = new FileReader(resourceFile.getPath());
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
            updateFile(updatedResourceFile);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isAmountInNumbers(String amountToTransfer) {
        return amountToTransfer.matches("\\d+$");
    }

    private static String concatenateSplitData(String[] splitData) {
        String dataToWrite = "";
        for (int i = 0; i < splitData.length; i++) {
            dataToWrite = dataToWrite.concat(splitData[i] + "-");
        }
        dataToWrite = dataToWrite.substring(0, dataToWrite.length() - 1);
        return dataToWrite;
    }
}
