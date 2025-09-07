package org.example.model;

public class Item {
    private String itemId;
    private int qty;

    public Item(String itemId, int qty) {
        this.itemId = itemId;
        this.qty = qty;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQty() {
        return qty;
    }
}