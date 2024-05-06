package com.example.isana.InventoryHistory;

public class InventoryHistoryAnswer {
    private String date_audit_inv;
    private String movement_audit_inv;
    private String price_audit_inv;
    private String added_stock;
    private String name_type;
    private String id_audit_inv;
    private String user_audit_inv;
    private String new_price_added;

    public String getNew_price_added() {
        return new_price_added;
    }

    public void setNew_price_added(String new_price_added) {
        this.new_price_added = new_price_added;
    }

    public String getDate_audit_inv() {
        return date_audit_inv;
    }

    public void setDate_audit_inv(String date_audit_inv) {
        this.date_audit_inv = date_audit_inv;
    }

    public String getMovement_audit_inv() {
        return movement_audit_inv;
    }

    public void setMovement_audit_inv(String movement_audit_inv) {
        this.movement_audit_inv = movement_audit_inv;
    }

    public String getPrice_audit_inv() {
        return price_audit_inv;
    }

    public void setPrice_audit_inv(String price_audit_inv) {
        this.price_audit_inv = price_audit_inv;
    }

    public String getAdded_stock() {
        return added_stock;
    }

    public void setAdded_stock(String added_stock) {
        this.added_stock = added_stock;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getId_audit_inv() {
        return id_audit_inv;
    }

    public void setId_audit_inv(String id_audit_inv) {
        this.id_audit_inv = id_audit_inv;
    }

    public String getUser_audit_inv() {
        return user_audit_inv;
    }

    public void setUser_audit_inv(String user_audit_inv) {
        this.user_audit_inv = user_audit_inv;
    }

    @Override
    public String toString() {
        return "InventoryHistoryAnswer{" +
                "date_audit_inv='" + date_audit_inv + '\'' +
                ", movement_audit_inv='" + movement_audit_inv + '\'' +
                ", price_audit_inv='" + price_audit_inv + '\'' +
                ", added_stock='" + added_stock + '\'' +
                ", name_type='" + name_type + '\'' +
                ", id_audit_inv='" + id_audit_inv + '\'' +
                ", user_audit_inv='" + user_audit_inv + '\'' +
                ", new_price_added='" + new_price_added + '\'' +
                '}';
    }
}
