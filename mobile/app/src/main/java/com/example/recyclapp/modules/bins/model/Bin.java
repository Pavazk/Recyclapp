package com.example.recyclapp.modules.bins.model;

import java.math.BigDecimal;

public class Bin {
    private Integer id;

    private Color color;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",");
        sb.append(color.toString()).append(",");
        sb.append(latitude.toString()).append(",");
        sb.append(longitude.toString());
        return sb.toString();
    }

    // Método para construir un objeto Bin a partir de una cadena
    public static Bin fromString(String str) {
        String[] parts = str.split(",");
        Bin bin = new Bin();
        bin.setId(Integer.parseInt(parts[0]));
        bin.setColor(Color.fromString(parts[1]));
        bin.setLatitude(new BigDecimal(parts[2]));
        bin.setLongitude(new BigDecimal(parts[3]));
        return bin;
    }
}
