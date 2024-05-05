package com.project.commons;

import com.project.commons.exceptions.CustomException;
import com.project.commons.messages.ErrorMessage;

public class Utils {
    private Utils() {
        //Se crea el constructor privado pues sera una clase que contenga solo metodos estaticos.
    }

    public static boolean isNotNullOrEmptyWithTrim(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isNullOrEmptyWithTrim(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static CustomException noDataValid() {
        return new CustomException(ErrorMessage.DATA_NO_VALID.getMessage());
    }

    public static CustomException throwException(String value) {
        return new CustomException(value);
    }

}
