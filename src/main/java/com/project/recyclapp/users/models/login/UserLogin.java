package com.project.recyclapp.users.models.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLogin {

    @NotBlank(message = "Por favor ingrese un email")
    @Email(message = "Por favor ingrese un email válido")
    private String email;

    @NotBlank(message = "Por favor ingrese su contraseña")
    @Size(max = 64)
    private String password;
}
