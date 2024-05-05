package com.project.recyclapp.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @Size(max = 255)
    @Column(nullable = false)
    private String email;

    @Size(max = 6)
    @Column(length = 6)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type")
    private UserType userType;

    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;

    @Size(max = 12)
    @Column(length = 12)
    private String identification;

    @Size(max = 64)
    @Column(length = 64)
    private String password;

    @Size(max = 12)
    @Column(length = 12)
    private String phone;

}