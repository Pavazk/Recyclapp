package com.example.isana.common;

public class Person {
    private static String user;
    private static String rol;

    public Person(String user, String rol){
        Person.user=user;
        Person.rol=rol;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Person.user = user;
    }

    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        Person.rol = rol;
    }
}
