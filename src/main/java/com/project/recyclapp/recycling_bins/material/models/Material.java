package com.project.recyclapp.recycling_bins.material.models;

import com.project.recyclapp.recycling_bins.bins.models.Bin;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @ColumnDefault("nextval('materials_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recycling_bins")
    private Bin bins;

}