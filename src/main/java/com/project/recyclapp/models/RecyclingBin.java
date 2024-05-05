package com.project.recyclapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recycling_bins")
public class RecyclingBin {
    @Id
    @ColumnDefault("nextval('recycling_bins_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color")
    private Color color;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

}