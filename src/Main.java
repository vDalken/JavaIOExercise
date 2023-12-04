import customexceptions.CardInfoNotFoundException;

import java.io.*;
import java.util.Scanner;

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
        Scanner scan = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler(RESOURCE_FILE.getPath());
        String userChoice;
        do {
            System.out.println("0. exit\n1. create account\n2. log in");
            userChoice = scan.nextLine();
            switch (userChoice) {
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
        } while (!userChoice.equals(EXIT));
    }

    private static void createAccount(Scanner scan, FileHandler fileHandler) {
        System.out.println("write your name");
        String name = scan.nextLine();
        Card newCard = new Card(name);
        System.out.println("\nYour Card Number: " + newCard.getCardNumber());
        System.out.println("\nYour Passcode: "+ newCard.getPasscode() + "\n");
        fileHandler.createAccount(name, newCard.getCardNumber(), newCard.getPasscode());
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
            throw new CardInfoNotFoundException();
        }
        if (loggedCard != null) {
            showLoggedMenu();
        }
    }

    private static void showLoggedMenu() {
        String userChoice;
        Scanner scan = new Scanner(System.in);
        ATM atm = new ATM(loggedCard, RESOURCE_FILE.getPath());
        do {
            System.out.println("0. Go Back\n1. Transfer\n2. Withdrawal\n3. Deposit\n4. Block Card\n5. Card Info");
            userChoice = scan.nextLine();
            switch (userChoice) {
                case EXIT:
                    System.out.println("\nYou're going back to the main menu\n");
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
        } while (!userChoice.equals("0"));
    }
}
