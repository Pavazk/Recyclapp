package com.project.recyclapp.commons.messages;

import lombok.Getter;

@Getter
public enum Defines {
    EXT_UFPS("@ufpso.edu.co");

    private final String message;

    Defines(String message) {
        this.message = message;
    }
}
