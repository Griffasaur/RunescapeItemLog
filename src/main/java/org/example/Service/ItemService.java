package org.example.Service;

import org.example.Api.ApiClient;
import org.example.Controller.ConsoleController;
import org.example.Dao.ItemDao;
import org.example.Dao.JdbcItemDao;
import org.example.Model.Item;
import org.example.Model.ItemPrice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemService {

    private final JdbcItemDao jdbcItemDao;
    private final ApiClient apiClient;
    private final ConsoleController consoleController;


    public ItemService (JdbcItemDao jdbcItemDao, ApiClient apiClient, ConsoleController consoleController) {
        this.jdbcItemDao = jdbcItemDao;
        this.apiClient = apiClient;
        this.consoleController = consoleController;
    }

    public List<Item> getAllItems() {
        return jdbcItemDao.getAllItems();
    }

    public Item getItemById(int id) {
        Item item = jdbcItemDao.getItemById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item with ID " + id + "not found.");
        }
        return item;
    }

    public Item getItemByName(String itemName) {
        Item item = jdbcItemDao.getItemByName(itemName);
        if (item == null) {
            throw new IllegalArgumentException("Item with name " + itemName + "not found.");
        }
        return item;
    }

    public List<Item> getItemsByName(String itemName) {
        List<Item> items = jdbcItemDao.getItemsByName(itemName);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items were found.");
        }

        return items;
    }

    public Item searchItemByName(String itemName) {
        List<Item> items = jdbcItemDao.getItemsByName(itemName);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items were found.");
        }
        consoleController.printLine("Search Results:");
        for (int i = 0; i <items.size(); i++) {
            consoleController.printLine((i +1) + ". " + items.get(i).getItemName());
        }

        int choice = consoleController.promptForInteger("Enter the number of the item you wish to select: ");

        if (choice < 1 || choice > items.size()) {
            consoleController.printLine("Invalid choice. Please select again.");
            return null;
        }

        return items.get(choice -1);
    }

    public List<Item> getItemsByBuyLimit(int buyLimit) {
        List<Item> items = jdbcItemDao.getItemsByBuyLimit(buyLimit);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items with that buy limit were found.");
        }
        return items;
    }

    public void populateItems() {
        List<Item> items = apiClient.getAllItems();
        if (items.isEmpty()) {
            throw new IllegalStateException("No items were found from the API.");
        }
        jdbcItemDao.insertItems(items);
    }

    public ItemPrice getLatestItemPrice(int id) {
        return apiClient.getLatestPricesById(id);
    }

    public ItemPrice get24HourItemPrice(int id) {
        return apiClient.get24HourPricesById(id);
    }
}
