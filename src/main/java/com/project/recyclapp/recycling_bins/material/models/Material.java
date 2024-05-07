package com.project.recyclapp.recycling_bins.material.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.recyclapp.recycling_bins.bins.models.Bin;
import com.project.recyclapp.recycling_bins.items.models.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Bin bins;

    @JsonIgnore
    @OneToMany(mappedBy = "material")
    private List<Item> items;
}