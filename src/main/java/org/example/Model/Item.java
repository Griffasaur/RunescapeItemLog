package org.example.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {


    int itemId;
    String itemName;
    int buyLimit;

    public Item(int itemId, String itemName, int buyLimit) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.buyLimit = buyLimit;
    }

    public Item () {
    };

    @JsonProperty("id")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @JsonProperty("name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @JsonProperty("limit")
    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    @Override
    public String toString() {
        return "org.example.Model.Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", buyLimit=" + buyLimit +
                '}';
    }
}
