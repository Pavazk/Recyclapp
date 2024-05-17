package com.example.recyclapp.models;

public class User {
    private String email;
    private String code;
    private String name;
    private String identification;
    private String password;

    public User(String email, String code, String name, String identification) {
        this.email = email;
        this.code = code;
        this.name = name;
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}