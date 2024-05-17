package com.project.recyclapp.modules.users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @Size(max = 255)
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Por favor ingrese un código")
    @Size(min =6, max = 6, message = "Por favor ingrese un código valido")
    private String code;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private UserType userType;

    @NotBlank(message = "Por favor ingrese el nombre")
    @Size(max = 150, message = "Limite de carácteres superado")
    private String name;

    @NotBlank(message = "Por favor ingrese el numero de identificación")
    @Size(min = 5, max = 12, message = "Por favor ingrese una identificación valida")
    private String identification;

    @Size(max = 64)
    private String password;

}