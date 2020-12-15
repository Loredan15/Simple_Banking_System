package banking;

import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        createAccount();

    }

    public static void printMenuWithoutLoging(){
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("3. Exit");
    }

    public static boolean createAccount(){
        boolean result = false;
        String pinCodeNumber = "";

        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter your PIN:");
        if (scanner.hasNext()) {
            pinCodeNumber = scanner.nextLine();
        }
        if (checkPinCode(pinCodeNumber)) result = true;

        return result;
    }

    private static boolean checkPinCode(String pinCode){
        boolean result = true;
        String regex = "^\\d{4}$";
        if (!pinCode.matches(regex)) result = false;

        return result;
    }
}