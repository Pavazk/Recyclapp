package com.project.recyclapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@Entity
@Table(name = "color", uniqueConstraints = {
        @UniqueConstraint(name = "color_name_key", columnNames = {"name"})
})
public class Color {
    @Id
    @ColumnDefault("nextval('color_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

}