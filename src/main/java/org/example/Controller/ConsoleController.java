package org.example.Controller;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleController {

    private static final Scanner input = new Scanner(System.in);

    public void printDivider() {
        System.out.println("------------------------------");
    }

    public boolean printStartMenu() {
        boolean nextStep = false;
        int prompt;

        printDivider();
        System.out.println("1: Start");
        System.out.println("2: Exit");
        printDivider();
        prompt = promptForInteger("What would you like to do?: ");

        if (prompt == 1 ) {
            nextStep = true;
        }

        return nextStep;
    }

    public int printMainMenu() {
        int prompt;

        printDivider();
        printLine("1: Lookup item information");
        printLine("2: Lookup price information");
        printLine("0: Exit");
        printDivider();
        prompt = promptForInteger("Please select an option: ");

        return prompt;
    }

    public int printItemMenu() {
        int prompt;

        printDivider();
        printLine("1: Search By Item Name");
        printLine("2: Search By Item Id");
        printLine("3: Search By Buy Limit");
        printLine("0: Exit");
        printDivider();
        prompt = promptForInteger("How would you like to search?: ");

        return prompt;

    }

    public int printPriceMenu() {
        int prompt;

        printDivider();
        printLine("1: Most Recent Price");
        printLine("2: 24 Hour Price and Volume");
        printLine("0: Exit");
        printDivider();
        prompt = promptForInteger("What price information would you like?: ");

        return prompt;

    }

    public String promptForInput(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    // Prompt user for an integer input
    public int promptForInteger(String prompt) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                value = input.nextInt();
                input.nextLine();
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.next(); // Clear the invalid input
            }
        }
        return value;
    }

    public void printLine(String text) {
        System.out.println(text);
    }

    public int promptForContinue() {
        int prompt;

        printDivider();
        printLine("1: Yes");
        printLine("2: No");
        printDivider();

        prompt = promptForInteger("Would you like to continue?: ");

        return prompt;
    }
}
