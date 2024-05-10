package com.project.recyclapp.users.models.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdate {

    @NotBlank(message = "Por favor ingrese un email")
    @Email(message = "Por favor ingrese un email válido")
    private String email;//M

    @NotBlank(message = "Por favor ingrese su contraseña")
    @Size(max = 64)
    private String oldPassword;//M

    private String newCode;//O

    private String newPassword;//O

    private String newPasswordConfirmation;//O

}
