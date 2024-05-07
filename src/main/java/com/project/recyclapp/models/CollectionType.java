package com.project.recyclapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@Entity
@Table(name = "collection_type", uniqueConstraints = {
        @UniqueConstraint(name = "collection_type_name_key", columnNames = {"name"})
})
public class CollectionType {
    @Id
    @ColumnDefault("nextval('collection_type_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

}