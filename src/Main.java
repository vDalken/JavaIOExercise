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
        FileHandler fileHandler = new FileHandler(resourceFile.getPath());
        Card newCard;
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
                    newCard = new Card(name);
                    fileHandler.writeOnTheFile(name, newCard.getCardNumber(), newCard.getPasscode());
                    break;
                case "2":
                    System.out.println("write your card number");
                    cardNumber = scan.nextLine();
                    System.out.println();
                    System.out.println("write your passcode");
                    passcode = scan.nextLine();
                    System.out.println();
                    ArrayList<String> info = new ArrayList<>();
                    if (fileHandler.isCardNumberValid(cardNumber, passcode)) {
                        info = fileHandler.getCardInfo(cardNumber);
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

    private static void showLoggedMenu() {
        String selectedOption;
        Scanner scan = new Scanner(System.in);
        ATM atm = new ATM(loggedCard,resourceFile.getPath());
        do {
            System.out.println("0. Go Back\n1. Transfer\n2. Withdrawal\n3. Deposit\n4. Block Card");
            selectedOption = scan.nextLine();
            switch (selectedOption) {
                case "0":
                    System.out.println("You're going back to the main menu");
                    break;
                case "1":
                    atm.transfer();
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
