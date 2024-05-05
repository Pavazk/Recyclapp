package com.project.commons.messages;

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
    //DocumentType
    DOCUMENT_TYPE_NO_EXISTS("Tipo de documento no existe");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
