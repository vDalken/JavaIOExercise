package classes;

import classes.Card;

import java.util.Scanner;

public class InputHelper {
    public static String getCardNumber() {
        Scanner scan = new Scanner(System.in);
        String cardNumber = "";

        System.out.println("\nPlease write the card number");
        cardNumber = scan.nextLine();
        cardNumber = cardNumber.trim();
        if (cardNumber.equals("0")) {
            return null;
        }
        return cardNumber;
    }

    public static String getAmountForWithdrawal(Card loggedCard) {
        Scanner scan = new Scanner(System.in);
        String amount;
        boolean needsToRepeat;
        do {
            System.out.println("\nPlease write the amount");
            amount = scan.nextLine();
            amount = amount.trim();
            if (!isAmountInNumbers(amount)) {
                needsToRepeat = true;
            } else {
                needsToRepeat = Integer.parseInt(amount) > loggedCard.getAccountBalance();
            }
            if (amount.equals("0")) {
                return null;
            }
        } while (needsToRepeat);
        return amount;
    }

    public static String getAmountForDeposit() {
        Scanner scan = new Scanner(System.in);
        String amount;
        boolean needsToRepeat=false;
        do {
            if(needsToRepeat){
                System.out.println("\nThe minimum cash deposit you can make is 10 euros\n");
            }
            System.out.println("\nPlease write the amount");
            amount = scan.nextLine();
            amount = amount.trim();
            if (!isAmountInNumbers(amount)) {
                needsToRepeat = true;
            } else {
                needsToRepeat = Integer.parseInt(amount) < 10;
            }
            if (amount.equals("0")) {
                return null;
            }
        } while (needsToRepeat);
        return amount;
    }

    public static String getAmountToTransfer(Card loggedCard) {
        Scanner scan = new Scanner(System.in);
        String amount;
        boolean needsToRepeat = false;
        do {
            System.out.println("\nPlease write the amount");
            amount = scan.nextLine();
            amount = amount.trim();
            if (!isAmountInNumbers(amount)) {
                needsToRepeat = true;
                System.out.println("\nPlease write a number");
            }
            if (amount.equals("0")) {
                return null;
            }
        } while (needsToRepeat);
        return amount;
    }

    public static boolean doesWantTheCardBlock() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nYou wont be able to login anymore and you'll have to get a new one");
        System.out.println("\nDo you want your card blocked? (write true or false)");
        String doesWantTheCardBlocked = scan.nextLine();
        doesWantTheCardBlocked = doesWantTheCardBlocked.trim();
        boolean does = false;
        if (isInBoolean(doesWantTheCardBlocked)) {
            does = Boolean.parseBoolean(doesWantTheCardBlocked);
        }
        return does;
    }

    private static boolean isAmountInNumbers(String amountToTransfer) {
        return amountToTransfer.matches("\\d+$");
    }

    private static boolean isInBoolean(String string) {
        return string.matches("true|false");
    }
}
