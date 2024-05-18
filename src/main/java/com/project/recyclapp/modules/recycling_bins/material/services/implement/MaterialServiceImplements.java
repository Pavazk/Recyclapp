package com.project.recyclapp.modules.recycling_bins.material.services.implement;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.commons.messages.ErrorMessage;
import com.project.recyclapp.modules.recycling_bins.bins.models.Bin;
import com.project.recyclapp.modules.recycling_bins.bins.repository.BinsRepository;
import com.project.recyclapp.modules.recycling_bins.material.models.Material;
import com.project.recyclapp.modules.recycling_bins.material.repository.MaterialRepository;
import com.project.recyclapp.modules.recycling_bins.material.services.interfaces.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImplements implements MaterialService {

    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    BinsRepository binsRepository;

    @Override
    public Material registerMaterial(Material material) {
        if (material == null || Utils.isNullOrEmptyWithTrim(material.getName())) {
            throw Utils.noDataValid();
        }

        return materialRepository.save(material);
    }

    @Override
    public Material updateMaterial(Material material) {
        Optional<Material> oldMaterial = materialRepository.findById(material.getId());
        if (oldMaterial.isEmpty()) {
            throw new CustomException(ErrorMessage.MATERIAL_NO_EXISTS.getMessage());
        }
        if (Utils.isNotNullOrEmptyWithTrim(material.getName())) {
            oldMaterial.get().setName(material.getName());
        }
        if (material.getBins() != null && material.getBins().getId() != null) {
            Optional<Bin> bin = binsRepository.findById(material.getBins().getId());
            if (bin.isPresent()) {
                oldMaterial.get().setBins(bin.get());
            }
        }
        return materialRepository.save(oldMaterial.get());
    }

    @Override
    public Material getMaterialById(Integer id) {
        Optional<Material> material = materialRepository.findById(id);
        if (material.isEmpty()) {
            throw new CustomException(ErrorMessage.MATERIAL_NO_EXISTS.getMessage());
        }
        return material.get();
    }

    @Override
    public String deleteMaterial(Material material) {
        materialRepository.delete(material);
        return "Item deleted";
    }

    @Override
    public List<Material> getAllMaterials() {
        return (List<Material>) materialRepository.findAll();
    }
}
