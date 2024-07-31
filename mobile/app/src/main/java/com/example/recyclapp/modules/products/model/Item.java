package com.example.recyclapp.modules.products.model;

import com.example.recyclapp.modules.bins.model.Color;

public class Item {
    private Integer id;
    private String name;
    private Color color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
