package org.example.Dao;

import org.example.Model.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getAllItems();

    Item getItemById(int itemId);

    List<Item> getItemsByName(String itemName);

    List<Item> getItemsByBuyLimit(int buyLimit);

    Item getItemByName (String itemName);

    void insertItems(List<Item> items);

}
