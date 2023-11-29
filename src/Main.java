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
                    System.out.println("write your passcode");
                    passcode = scan.nextLine();
                    ArrayList<String> info = new ArrayList<>();
                    if (isCardNumberValid(cardNumber, passcode)) {
                        info = getCardInfo(cardNumber);
                        boolean isCardBlocked = info.get(4).equals("true");
                        loggedCard = new Card(info.get(0), cardNumber, info.get(2), Integer.parseInt(info.get(3)), isCardBlocked);
                    } else {
                        System.out.println("Account doesn't exist");
                    }
                    if (loggedCard != null) {
                        showLoggedMenu();
                    }
                    break;
                case "3":
                    break;
                case "4":
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
            String dataToWrite = name + "-" + cardNumber + "-" + passcode + "-0-false";
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
            System.out.println("0. Exit\n1. Transfer\n2. Withdrawal\n3. Deposit\n4. Block Card");
            Scanner scan = new Scanner(System.in);
            selectedOption = scan.nextLine();
            switch (selectedOption) {
                case "0":
                    System.out.println("You're going back to the main menu");
                    break;
                case "1":
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
}
