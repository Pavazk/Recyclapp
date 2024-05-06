package com.example.isana.Inventory;

public class InventoryAnswer {
    private String id_item;
    private String date_item;
    private String current_stock_item;
    private String price_item;
    private String user_item;
    private String name_type;
    private String name_cat;

    public InventoryAnswer(String id_item, String date_item, String stock_item, String price_item, String user_item, String name_type, String name_cat) {
        this.id_item = id_item;
        this.date_item = date_item;
        this.current_stock_item = stock_item;
        this.price_item = price_item;
        this.user_item = user_item;
        this.name_type = name_type;
        this.name_cat = name_cat;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getDate_item() {
        return date_item;
    }

    public void setDate_item(String date_item) {
        this.date_item = date_item;
    }

    public String current_stock_item() {
        return current_stock_item;
    }

    public void setStock_item(String stock_item) {
        this.current_stock_item = stock_item;
    }

    public String getPrice_item() {
        return price_item;
    }

    public void setPrice_item(String price_item) {
        this.price_item = price_item;
    }

    public String getUser_item() {
        return user_item;
    }

    public void setUser_item(String user_item) {
        this.user_item = user_item;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getName_cat() {
        return name_cat;
    }

    public void setName_cat(String name_cat) {
        this.name_cat = name_cat;
    }
}
