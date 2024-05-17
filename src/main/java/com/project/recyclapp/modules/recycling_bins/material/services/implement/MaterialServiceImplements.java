package com.project.recyclapp.modules.recycling_bins.material.services.implement;

import com.project.recyclapp.modules.recycling_bins.material.models.Material;
import com.project.recyclapp.modules.recycling_bins.material.services.interfaces.MaterialService;

import java.util.List;

public class MaterialServiceImplements implements MaterialService {
    @Override
    public Material registerMaterial(Material material) {
        return null;
    }

    @Override
    public Material updateMaterial(Material material) {
        return null;
    }

    @Override
    public Material getMaterialById(Material material) {
        return null;
    }

    @Override
    public String deleteMaterial(Material material) {
        return "";
    }

    @Override
    public List<Material> getAllMaterials() {
        return List.of();
    }
}
