package com.example.recyclapp.modules.menus;


public class item_menu {
    private String text;
    private int img;

    public item_menu(String text, int img) {
        this.text = text;
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
