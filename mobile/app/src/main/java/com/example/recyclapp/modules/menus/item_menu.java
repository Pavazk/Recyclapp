package com.example.recyclapp.modules.menus;

import android.graphics.drawable.Drawable;

public class item_menu {
    private String texto;
    private int img;

    public item_menu(String texto, int img) {
        this.texto = texto;
        this.img = img;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
