package com.project.recyclapp.modules.recycling_bins.bins.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    private String name;

    @OneToMany(mappedBy = "color")
    @JsonIgnore
    private List<Bin> bins;

    @OneToMany(mappedBy = "color")
    @JsonIgnore
    private List<Item> item;
}