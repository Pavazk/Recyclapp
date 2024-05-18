package com.project.recyclapp.modules.recycling_bins.material.services.interfaces;

import com.project.recyclapp.modules.recycling_bins.material.models.Material;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialService {
    Material registerMaterial(Material material);

    Material updateMaterial(Material material);

    Material getMaterialById(Integer id);

    String deleteMaterial(Material material);

    List<Material> getAllMaterials();
}
