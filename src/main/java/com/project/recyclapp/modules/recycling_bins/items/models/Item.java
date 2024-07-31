package com.project.recyclapp.modules.recycling_bins.items.models;

import com.project.recyclapp.modules.recycling_bins.bins.models.Bin;
import com.project.recyclapp.modules.recycling_bins.bins.models.Color;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Color color;
}