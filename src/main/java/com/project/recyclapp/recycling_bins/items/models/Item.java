package com.project.recyclapp.recycling_bins.items.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.recyclapp.recycling_bins.material.models.Material;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @Column(insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "id")
    private Item subitem;

    @JsonIgnore
    @OneToMany(mappedBy = "subitem")
    private List<Item> listItems;

}