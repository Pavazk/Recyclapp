package com.example.isana.login;

public class EnvioLogin {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnvioLogin(String user, String password) {
        this.user = user;
        this.password = password;
    }

    private String user;
    private String password;

    @Override
    public String toString() {
        return "{\"user\":\"" + user + "\",\"password\":\"" + password + "\"}";
    }
}
