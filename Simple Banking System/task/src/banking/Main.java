package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Main {

  static Scanner scanner = new Scanner(System.in);
  static Map<String, String> database = new HashMap<>();

  public static void main(String[] args) {
  mainMenu();
//        for (Map.Entry<String, String> entry : database.entrySet()){
//            System.out.println(entry);
//        }

  }

  public static void mainMenu() {
    printMenuWithoutLogin();
    while (true) {

      int itemMenu = scanner.nextInt();
      switch (itemMenu) {
        case 1:
          createAccount();
          printMenuWithoutLogin();
          break;
        case 2:
          login();
          break;
        case 3:
          System.out.println("\nBye!");
          System.exit(1);
        default:
          System.out.println("Error: incorrect item menu");
      }
    }

  }

  public static void printMenuWithoutLogin() {
    System.out.println("1. Create an account");
    System.out.println("2. Log into account");
    System.out.println("3. Exit");
  }

  public static void printMenuWithLogin() {
    System.out.println("1. Balance");
    System.out.println("2. Log out");
    System.out.println("0. Exit");
  }

  public static boolean createAccount() {
    boolean result = false;
    String cardNumber = "";
    String pinCodeNumber = "";

//    System.out.println("Enter your card number:");
//    cardNumber = scanner.next();
//
//    System.out.println("Enter your PIN:");
//    pinCodeNumber = scanner.next();
//
//    if (checkPinCode(pinCodeNumber) && checkCardNumber(cardNumber)) {
//      result = true;
//      System.out.println("Your card has been created");
//      System.out.println("Your card number:");
//      System.out.println(cardNumber);
//      System.out.println("Your card PIN:");
//      System.out.println(pinCodeNumber + "\n");
//      database.put(cardNumber, pinCodeNumber);
//    } else {
//      System.out.println("Error: incorrect input");
//    }

    cardNumber = generateCardNumber();
    pinCodeNumber = "" + ThreadLocalRandom.current().nextInt(1001, 10000);

    System.out.println("Your card has been created");
    System.out.println("Your card number:");
    System.out.println(cardNumber);
    System.out.println("Your card PIN:");
    System.out.println(pinCodeNumber + "\n");
    database.put(cardNumber, pinCodeNumber);

    return result;
  }

  private static boolean checkPinCode(String pinCode) {
    boolean result = true;
    String regex = "^\\d{4}$";
    if (!pinCode.matches(regex)) {
      result = false;
    }

    return result;
  }

  private static boolean checkCardNumber(String cardNumber) {
    boolean result = false;

    String iin = cardNumber.substring(0, 6);
    if (iin.equals("400000")) {
      result = true;
    }

    return result;
  }

  private static void login() {

    System.out.println("Enter your card number:");
    String cardNumber = scanner.next();

    System.out.println("Enter your PIN:");
    String pinCode = scanner.next();

    if (!database.containsKey(cardNumber) || (!database.containsValue(pinCode))) {
      System.out.println("Wrong card number or PIN!\n");
    } else {
      System.out.println("You have successfully logged in!\n");
      printMenuWithLogin();
      String itemMenuLogin = "";

      while (true) {
        itemMenuLogin = scanner.next();
        switch (itemMenuLogin) {
          case "1":
            System.out.println("Balance: 0\n");
            printMenuWithLogin();
            break;
          case "2":
            System.out.println();
            mainMenu();
            break;
          case "0":
            System.exit(1);
          default:
            System.out.println("Error: incorrect item menu");
        }
      }
    }
  }

  private static String generateCardNumber() {
    boolean numberIsUnique = false;
    String inn = "400000";
    long generatedNumber;
    List<Long> intArray = new ArrayList<>();
    String result = "unknown number";

    while (!numberIsUnique) {

      generatedNumber = ThreadLocalRandom.current().nextLong(1_000_000_00, 9_999_999_99);

      //Костыль какой-то по преобразованию в массив чисел. Наверняка, есть готовый метод

      while (generatedNumber > 0) {
        long temp = generatedNumber % 10;
        intArray.add(temp);
        generatedNumber /= 10;
      }
      //===========================конец костыля=========================================

      //Находим контрольную сумму по алгоритму Luhn
      long sumNumber = 0;
      long checkSumNumber = 0;
      long remainderOfTheDivision = 0;

      for (int i = 0; i < intArray.size(); i++) {
        if (intArray.get(i) % 2 == 0) {
          intArray.set(i, intArray.get(i) * 2);
        }
      }
      for (int i = 0; i < intArray.size(); i++) {
        if (intArray.get(i) > 9) {
          intArray.set(i, intArray.get(i) - 9);
        }
        sumNumber += intArray.get(i);
      }
      remainderOfTheDivision = sumNumber % 10;
      checkSumNumber = 10 - remainderOfTheDivision;
      if (checkSumNumber == 10) {
        checkSumNumber = 0;
      }
      intArray.add(checkSumNumber);

      result = inn;
      for (int i = 0; i < intArray.size(); i++) {
        result += intArray.get(i);
      }

      if (!database.containsKey(result)) {
        numberIsUnique = true;
      }
    }

    return result;
  }


}