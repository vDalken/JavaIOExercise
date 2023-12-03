import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static final String EXIT = "0";
    private static final String CREATE_ACCOUNT = "1";
    private static final String TRANSFER = "1";
    private static final String WITHDRAWAL = "2";
    private static final String DEPOSIT = "3";
    private static final String BLOCK_CARD = "4";
    private static final String CARD_INFO = "5";
    private static final String LOGIN = "2";
    private static final File RESOURCE_FILE = new File("resources/text");

    static Card loggedCard;

    public static void main(String[] args) {
        // OPEN AN INPUT STREAM WITH A FILE PATH AS THE SOURCE
        Scanner scan = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler(RESOURCE_FILE.getPath());
        String selectedOption;
        do {
            System.out.println("0. exit\n1. create account\n2. log in");
            selectedOption = scan.nextLine();
            switch (selectedOption) {
                case EXIT:
                    break;
                case CREATE_ACCOUNT:
                    createAccount(scan, fileHandler);
                    break;
                case LOGIN:
                    login(scan, fileHandler);
                    break;
                default:
                    System.out.println("\nPlease, type in a valid option\n");
                    break;
            }
        } while (!selectedOption.equals(EXIT));
    }

    private static void createAccount(Scanner scan, FileHandler fileHandler) {
        System.out.println("write your name");
        String name = scan.nextLine();
        Card newCard = new Card(name);
        System.out.println("\nYour Card Number: " + newCard.getCardNumber());
        System.out.println("\nYour Passcode: "+ newCard.getPasscode() + "\n");
        fileHandler.writeOnTheFile(name, newCard.getCardNumber(), newCard.getPasscode());
    }

    private static void login(Scanner scan, FileHandler fileHandler) {
        System.out.println("write your card number");
        String cardNumber = scan.nextLine();
        System.out.println();
        System.out.println("write your passcode");
        String passcode = scan.nextLine();
        System.out.println();
        if (fileHandler.isCardNumberValid(cardNumber, passcode)) {
            loggedCard = fileHandler.getCardInfo(cardNumber);
            System.out.println("Welcome " + loggedCard.getName() + "\n");
        } else {
            System.out.println("\nAccount with those details doesn't exist");
            System.out.println("Make sure you're typing in the right card number and passcode\n");
        }
        if (loggedCard != null) {
            showLoggedMenu();
        }
    }

    private static void showLoggedMenu() {
        String selectedOption;
        Scanner scan = new Scanner(System.in);
        ATM atm = new ATM(loggedCard, RESOURCE_FILE.getPath());
        do {
            System.out.println("0. Go Back\n1. Transfer\n2. Withdrawal\n3. Deposit\n4. Block Card\n5. Card Info");
            selectedOption = scan.nextLine();
            switch (selectedOption) {
                case EXIT:
                    System.out.println("You're going back to the main menu");
                    break;
                case TRANSFER:
                    atm.transfer();
                    break;
                case WITHDRAWAL:
                    atm.withdrawal();
                    break;
                case DEPOSIT:
                    atm.deposit();
                    break;
                case BLOCK_CARD:
                    atm.blockCard();
                    break;
                case CARD_INFO:
                    atm.showCardInfo();
                    break;
                default:
                    System.out.println("You typed something that isn't valid as an option");
                    break;
            }
        } while (!selectedOption.equals("0"));
    }
}
