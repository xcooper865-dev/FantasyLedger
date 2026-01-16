package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // hunbals changes

    public static ArrayList<Transactions> transactionHistory = getTransactionFromFile();

    //━━━━━━━━━━━━━━━━━━━━⊱⋆Main Menu and Choices⋆⊰━━━━━━━━━━━━━━━━━━━━
    public static void main(String[] args) {
        System.out.println(
                "╭──────────────────────.★..───╮\n" +
                "  Welcome to Fairy's Treasury\n" +
                "╰───..★.──────────────────────╯\n");

        System.out.println(
                "You push open the wooden door of a cozy little shop...\n" +
                "A bell jingles, and a fairy clerk flutters over with a smile.\n");

        String mainMenu =
                "━━━━━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━━━━━\n" +
                "⋆｡ﾟ☁｡⋆☾｡ The Enchanted Counter ⋆｡ﾟ☁｡⋆｡☾\n" +
                "D) Add Treasure Deposit\n" +
                "P) Make Treasure Payment\n" +
                "L) Treasury of Trades (Ledger)\n" +
                "X) Exit\n" +
                "━━━━━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━━━━━\n";


        while (true) {
            System.out.print(mainMenu);
            char command;

            command = ConsoleHelper.promptForChar("'What service do you seek today traveler?'\n" + "You");

            switch (command) {
                case 'D':
                    addDeposit();//go to deposit
                    break;
                case 'P':
                    makePayment();//go make payment
                    break;
                case 'L':
                    goToLedger();//go to ledger screen
                    break;
                case 'X': //exit
                    return;
                default:
                    System.out.println("'We don't have that service here...'");
                    break;

            }
        }
    }
    private static void addDeposit() {
        String description = ConsoleHelper.promptForString
        ("'Ah, adding to your treasures, are we?'\n" +
        "'Please describe this fortune's purpose.'\n" + "You");

        String vendor = ConsoleHelper.promptForString
        ("'From which realm, shop, or kind soul does this gold arrive?'\n" + "You");

        Double amount = ConsoleHelper.promptForDouble
        ("'And how many shimmering coins shall we add to your pouch?'\n" + "You");

        System.out.println("'Thank you! I've stored your information in the Treasury of Trades'");

        //Calling the method to save info to file
        saveTransactionFromUser(description, vendor, amount);
    }
    private static void makePayment(){
        Scanner scanner = new Scanner(System.in);

        String description = ConsoleHelper.promptForString
        ("'Ah, sending some treasure out to a lucky soul I see!\n" +
        "'Please describe this fortune's purpose.'\n" + "You");

        String vendor = ConsoleHelper.promptForString
        ("'To which realm, shop, or kind soul does this gold go?'\n" + "You");

        System.out.print("'And how many shimmering coins shall we add to your pouch?'\n" + "You: ");
        //This will make sure that the money will show it's negative (aka losing money)
        Double amount = scanner.nextDouble() * -1;

        System.out.println("'Thank you! I've stored your information in the Treasury of Trades'");

        //Calling the method to save info to file
        saveTransactionFromUser(description, vendor, amount);
    }

    //━━━━━━━━━━━━━━━━━━━━⊱⋆Methods for Reading/Writing Info to File⋆⊰━━━━━━━━━━━━━━━━━━━━
    private static void saveTransactionFromUser(String description, String vendor, Double amount){
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Transactions t = new Transactions(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            transactionHistory.add(t);

            bufferedWriter.newLine();

            bufferedWriter.write(t.getDate() + "|"
                    + t.getTime() + "|"
                    + t.getDescription() + "|"
                    + t.getVendor() + "|"
                    + t.getAmount());
            bufferedWriter.close();

        } catch (Exception e){
            System.out.println("There was a problem, try again!");
        }
    }
    private static ArrayList<Transactions> getTransactionFromFile () {
        ArrayList<Transactions> transactionHistory = new ArrayList<Transactions>();
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String lineFromString;

            while ((lineFromString = bufferedReader.readLine()) != null) {
                String[] part = lineFromString.split("\\|");
                LocalDate date = LocalDate.parse(part[0]);
                LocalTime time = LocalTime.parse(part[1]);
                String description = part[2];
                String vendor = part[3];
                double amount = Double.parseDouble(part[4]);

                Transactions t = new Transactions(date, time, description, vendor, amount);
                transactionHistory.add(t);
            }

        } catch (Exception e) {
            System.out.println("There was a problem, try again!" + e.getLocalizedMessage());
        }
        return transactionHistory;
    }

    //━━━━━━━━━━━━━━━━━━━━⊱⋆Ledger Menu and Choices⋆⊰━━━━━━━━━━━━━━━━━━━━
    public static void goToLedger() {
        String ledgerMenu =
                "━━━━━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━━━━━\n" +
                "⋆｡ﾟ☁｡⋆☾｡ Treasury of Trades (Ledger)⋆｡ﾟ☁｡⋆｡☾\n" +
                "A) All Entries\n" +
                "D) Treasure Deposits \n" +
                "P) Treasure Payments \n" +
                "R) Records Chamber (Reports) \n" +
                "H) Back - to (Home) The Enchanted Counter \n" +
                "━━━━━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━━━━━\n";
        while (true) {
            System.out.print(ledgerMenu);
            char command;

            command = ConsoleHelper.promptForChar
            ("Your wish is the fairy's command - which entries shall we reveal?\n" + "You");

            switch (command) {
                case 'A':
                    viewAllLedger();//see all together
                    break;
                case 'D':
                    viewDeposits();//go to deposit
                    break;
                case 'P':
                    viewPayments();//go to payments
                    break;
                case 'R':
                    viewReports();//go to reports
                    break;
                case 'H':
                    return;
                default:
                    System.out.println("That isn't something I can do...");
                    break;
            }
        }
    }
    private static void viewAllLedger(){
        for(Transactions t : transactionHistory){
            System.out.println(t);
        }
        System.out.println();
        System.out.println("Every magical entry glows before your eyes - behold your transactions!");
    }
    private static void viewDeposits (){

        for (Transactions t : transactionHistory) {
            if (t.getAmount() >= 0) {
                System.out.println(t);
            }
        }
        System.out.println();
        System.out.println("The fairy sprinkles a little magic dust...\n" +
        "'Here are all the treasures you've safely stored! What a fine collection of riches, traveler!'");
    }
    private static void viewPayments (){

        for (Transactions t : transactionHistory){
            if (t.getAmount() <= 0){
                System.out.println(t);
            }
        }
        System.out.println();
        System.out.println("The fairy waves her wand...\n" +
        "'Here are all of the coins you've sent in generosity!'");
    }

    //━━━━━━━━━━━━━━━━━━━━⊱⋆Reports Menu and Choices⋆⊰━━━━━━━━━━━━━━━━━━━━
    public static void viewReports(){
        String ledgerMenu =
                "━━━━━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━━━━━\n" +
                "⋆｡ﾟ☁｡⋆☾｡ Records Chamber (Reports)⋆｡ﾟ☁｡⋆｡☾\n" +
                "1) Month To Date \n" +
                "2) Previous Month \n" +
                "3) Year To Date \n" +
                "4) Previous Year \n" +
                "5) Seek Trades by Merchant Name \n" +
                "0) Back - to Treasury of Trades (Ledger)\n" +
                "━━━━━━━━━━━━━━━━━━━━⊱⋆⊰━━━━━━━━━━━━━━━━━━━━\n";
        while (true) {
            System.out.print(ledgerMenu);
            int command;

            command = ConsoleHelper.promptForInt
            ("Speak your desire, and the Archives shall shimmer with the results.\n" + "You");

            switch (command) {
                case 1:
                    viewMonthToDate();//see all together
                    break;
                case 2:
                    viewPreviousMonth();//go to deposit
                    break;
                case 3:
                    viewYearToDate();//go to payments
                    break;
                case 4:
                    viewPreviousYear();//go to reports
                    break;
                case 5:
                    searchByVendor();//type in vendor
                    break;
                case 0:
                    return;
                default:
                    System.out.println("I'm not sure what to do with that...");
                    break;
            }
        }
    }
    private static void viewMonthToDate(){

        LocalDate today = LocalDate.now();
        for (Transactions t: transactionHistory) {
            if (t.getDate().getYear() == today.getYear() &&  // Check if the transaction happened in the current year
                    t.getDate().getMonth() == today.getMonth()) {
                System.out.println(t);    // Print the transaction details if it matches the current year
            }
        }
        System.out.println();
        System.out.println("The fairy's scroll has revealed every coin and trade from this moon cycle.");
    }
    private static void viewPreviousMonth(){

        LocalDate today =LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        for (Transactions t : transactionHistory){
            if (t.getDate().getYear() == lastMonth.getYear() &&
                    t.getDate().getMonth() ==  lastMonth.getMonth()) {
                System.out.println(t);
            }
        }
        System.out.println();
        System.out.println("The fairy's scroll has revealed every coin and trade from the last moon cycle.");
    }
    private static void viewYearToDate(){

        int currentYear = LocalDate.now().getYear();
        for (Transactions t : transactionHistory){
            if(t.getDate().getYear() == currentYear) {
                System.out.println(t);
            }
        }
        System.out.println();
        System.out.println("Every coin and trade from this year sparkles before you - what a treasure trove!");
    }
    private static void viewPreviousYear(){

        int lastYear = LocalDate.now().getYear() -1;
        for (Transactions t : transactionHistory){
            if(t.getDate().getYear() == lastYear) {
                System.out.println(t);
            }
        }
        System.out.println();
        System.out.println("Every coin and trade from last year sparkles before you - what a treasure trove!");

    }
    private static void searchByVendor(){
        System.out.println("The fairy clerk flips through the glowing pages...");
        String vendor = ConsoleHelper.promptForString("'Whose trades shall I reveal today traveler?'\n" + "You");

        boolean isfind = false;
        for (Transactions t : transactionHistory) {
            if(t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t);
                isfind = true;
            }
        }

        System.out.println();
        if(isfind) {
            System.out.println("'Here's what I've found for " + vendor + "'");
        } else {
            System.out.println("The fairy looks down with furrowed eyebrows then back up at you.\n" +
            "'It seems that your merchant isn't found here...'");
        }
    }
}
