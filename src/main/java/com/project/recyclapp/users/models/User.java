package com.project.recyclapp.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @Size(max = 6)
    @Column(length = 6)
    private String code;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private UserType userType;

    @Size(max = 150)
    @Column(length = 150)
    private String name;

    @Size(max = 12)
    @Column(length = 12)
    private String identification;

    @Size(max = 64)
    @Column(length = 64)
    @JsonIgnore
    private String password;

}