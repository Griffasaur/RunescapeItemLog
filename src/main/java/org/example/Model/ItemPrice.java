package org.example.Model;

public class ItemPrice {

    Item item;

    int lowPrice;

    int highPrice;

    int priceDifference;

    int lowVolume;

    int highVolume;

    int totalVolume;

    public ItemPrice(Item item, int lowPrice, int highPrice, int lowVolume, int highVolume) {
        this.item = item;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.priceDifference = highPrice - lowPrice;
        this.lowVolume = lowVolume;
        this.highVolume = highVolume;
        this.totalVolume = lowVolume + highVolume;
    }

    public ItemPrice() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(int lowPrice) {
        this.lowPrice = lowPrice;
    }

    public int getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(int highPrice) {
        this.highPrice = highPrice;
    }

    public int getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(int highPrice, int lowPrice) {
        this.priceDifference = highPrice - lowPrice;
    }


    public int getLowVolume() {
        return lowVolume;
    }

    public void setLowVolume(int lowVolume) {
        this.lowVolume = lowVolume;
    }

    public int getHighVolume() {
        return highVolume;
    }

    public void setHighVolume(int highVolume) {
        this.highVolume = highVolume;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(int lowVolume, int highVolume) {
        this.totalVolume = lowVolume + highVolume;
    }
}
