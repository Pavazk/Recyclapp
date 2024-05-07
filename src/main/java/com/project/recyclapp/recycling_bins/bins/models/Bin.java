package com.project.recyclapp.recycling_bins.bins.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.recyclapp.recycling_bins.material.models.Material;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Bin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Color color;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "bins")
    private List<Material> materials;
}