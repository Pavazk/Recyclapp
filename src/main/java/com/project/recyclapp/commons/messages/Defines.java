package com.project.recyclapp.commons.messages;

import lombok.Getter;

@Getter
public enum Defines {
    EXT_UFPS("@ufpso.edu.co"),
    ROLE_TEACHER("Docente"),
    ROLE_ADMIN("Administrativos"),
    ROLE_STUDENT("Estudiante"),
    ROLE_SUPER_ADMIN("Super Administrador"),;


    private final String message;

    Defines(String message) {
        this.message = message;
    }
}
