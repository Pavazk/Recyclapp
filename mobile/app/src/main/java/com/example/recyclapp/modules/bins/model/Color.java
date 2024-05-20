package com.example.recyclapp.modules.bins.model;

public class Color {
    private Integer id;

    private String name;

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("|");
        sb.append(name);
        return sb.toString();
    }

    public static Color fromString(String str) {
        String[] parts = str.split("\\|");
        Color color = new Color();
        color.setId(Integer.parseInt(parts[0]));
        color.setName(parts[1]);
        return color;
    }
}
