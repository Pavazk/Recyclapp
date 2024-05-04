package com.project.recyclapp.models;

import com.project.recyclapp.models.enums.DocumentType;
import com.project.recyclapp.models.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String code;
    @NotNull
    @Column(unique = true, nullable = false, length = 20, name = "first_name", columnDefinition = "TEXT")
    private String firstName;
    private String lastName;

    private DocumentType documentType;

    private String identification;

    private String email;
    private String password;

    private String phone;

    private UserType userType;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
}
