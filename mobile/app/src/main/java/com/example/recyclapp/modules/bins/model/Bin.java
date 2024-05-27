package com.example.recyclapp.modules.bins.model;

public class Bin {
    private Integer id;

    private Color color;

    private Double latitude;

    private Double longitude;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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
        bin.setLatitude(Double.parseDouble(parts[2]));
        bin.setLongitude(Double.parseDouble(parts[3]));
        return bin;
    }
}
