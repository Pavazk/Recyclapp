package com.project.recyclapp.commons.messages;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    DATA_NO_VALID("Datos proporcionados no son validos"),
    //User
    ALREADY_EXISTS_CODE("Ya existe un usuario registrado con ese codigo"),
    ALREADY_EXISTS_EMAIL("Ya existe un usuario registrado con ese correo"),
    USER_NO_EXISTS("Usuario no existe"),
    //UserType
    USER_TYPE_NO_EXISTS("Tipo de usuario no existe"),
    USER_TYPE_NO_VALID("Tipo de usuario no valido"),
    //Bins
    BIN_NO_EXISTS("Caneca no existe"),
    //Material
    MATERIAL_NO_EXISTS("Material no existe"),
    //Item
    ITEM_NO_EXISTS("Producto no existe"),
    //Color
    COLOR_NO_VALID("Color no valido"),
    COLOR_NO_EXISTS("Color no existe");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
