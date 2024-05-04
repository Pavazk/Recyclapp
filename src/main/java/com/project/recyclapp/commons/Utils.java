package com.project.recyclapp.commons;

import com.project.recyclapp.models.User;

public class Utils {
    private Utils() {
        //Se crea el constructor privado pues sera una clase que contenga solo metodos estaticos.
    }

    public static boolean isNotNullOrEmptyWithTrim(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidUser(User user){
        return true;
    }

}
