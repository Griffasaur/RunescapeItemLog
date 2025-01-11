package org.example.App;

import org.example.Controller.ConsoleController;
import org.example.Model.Item;
import org.example.Model.ItemPrice;
import org.example.Service.ItemService;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class ApplicationMenu {

    private static final Scanner input = new Scanner(System.in);

    private final ConsoleController consoleController = new ConsoleController();

    private final ItemService itemService;

    public ApplicationMenu(ItemService itemService) {
        this.itemService = itemService;
    }

    public void printMainMenu() {
        itemService.populateItems();
        boolean nextStep = consoleController.printStartMenu();

        while (nextStep) {
            int mainMenuPrompt = consoleController.printMainMenu();
            switch (mainMenuPrompt) {
                case 0:
                    consoleController.printLine("Exiting application.");
                    nextStep = false;
                    break;
                case 1:
                    handleItemMenu();
                    break;
                case 2:
                    handlePriceMenu();
                    break;
                default:
                    consoleController.printLine("Invalid input. Please try again.");
            }
        }
        System.exit(0);
    }

    private void handleItemMenu() {
        int itemMenuPrompt = consoleController.printItemMenu();
        switch (itemMenuPrompt) {
            case 1:
                handleItemSearchByName();
                break;
            case 2:
                handleItemSearchById();
                break;
            case 3:
                handleItemSearchByBuyLimit();
                break;
            default:
                consoleController.printLine("Returning to main menu.");
        }
    }

    private void handleItemSearchByName() {
        try {
            String itemName = consoleController.promptForInput("What is the name of the item you are searching for?: ");
            List<Item> items = itemService.getItemsByName(itemName);
            consoleController.printDivider();
            consoleController.printLine("Item Details");
            if (items.isEmpty()) {
                consoleController.printLine("No items found with that name.");
            } else {
                for (Item item : items) {
                    printItemInformation(item);
                }
            }
        } catch (IllegalArgumentException e) {
            consoleController.printLine(e.getMessage());
        }
    }

    private void handleItemSearchById() {
        try {
            int itemId = consoleController.promptForInteger("What is the ID of the item you are searching for?: ");
            Item item = itemService.getItemById(itemId);
            printItemInformation(item);
        } catch (IllegalArgumentException e) {
            consoleController.printLine(e.getMessage());
        }
    }

    private void handleItemSearchByBuyLimit() {
        try {
            int buyLimit = consoleController.promptForInteger("What is the buy limit of items you would like to search for?: ");
            List<Item> items = itemService.getItemsByBuyLimit(buyLimit);
            for (Item item : items) {
                if (items.isEmpty()) {
                    consoleController.printLine("No items found with that buy limit.");
                } else {
                    printItemInformation(item);
                }
            }
        } catch (IllegalArgumentException e) {
            consoleController.printLine(e.getMessage());
        }
    }

    private void handlePriceMenu() {
        try {
            String prompt = consoleController.promptForInput("What is the name of the item you would like to search for?: ");
            Item item = itemService.searchItemByName(prompt);

            int priceMenuPrompt = consoleController.printPriceMenu();
            switch (priceMenuPrompt) {
                case 1:
                    handleLatestPrice(item);
                    break;
                case 2:
                    handle24HourPrice(item);
                    break;
                default:
                    consoleController.printLine("Returning to main menu.");
            }
        } catch (IllegalArgumentException e) {
            consoleController.printLine(e.getMessage());
        }
    }

    private void handleLatestPrice(Item item) {
        try {
            ItemPrice itemPrice = itemService.getLatestItemPrice(item.getItemId());
            printPriceInformation(item, itemPrice);
        } catch (IllegalArgumentException e) {
            consoleController.printLine(e.getMessage());
        }
    }

    private void handle24HourPrice(Item item) {
        try {
            ItemPrice itemPrice = itemService.get24HourItemPrice(item.getItemId());
            printPriceInformation(item, itemPrice);
        } catch (IllegalArgumentException e) {
            consoleController.printLine(e.getMessage());
        }

    }

    public void printItemInformation(Item item) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        consoleController.printDivider();
        if (item == null) {
            consoleController.printLine("No item found.");
        } else {
            consoleController.printLine("Item: " + item.getItemName());
            consoleController.printLine("Buy Limit: " + numberFormat.format(item.getBuyLimit()));
            consoleController.printLine("Item ID: " + item.getItemId());
        }
        consoleController.printDivider();

    }

    public void printPriceInformation(Item item, ItemPrice itemPrice) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        if (item == null || itemPrice == null) {
            consoleController.printLine("No price information available.");
        } else {
            consoleController.printDivider();
            consoleController.printLine("Item: " + item.getItemName());
            consoleController.printLine("High Price: " + numberFormat.format(itemPrice.getHighPrice()));
            consoleController.printLine("Low Price: " + numberFormat.format(itemPrice.getLowPrice()));
            if (itemPrice.getHighVolume() > -1 && itemPrice.getLowVolume() > -1) {
                consoleController.printLine("High Volume: " + numberFormat.format(itemPrice.getHighVolume()));
                consoleController.printLine("Low Volume: " + numberFormat.format(itemPrice.getLowVolume()));
            }
        }
    }
}
