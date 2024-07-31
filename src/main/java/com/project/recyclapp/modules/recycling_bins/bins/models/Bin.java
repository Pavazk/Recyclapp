package com.project.recyclapp.modules.recycling_bins.bins.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Double latitude;

    private Double longitude;
}