package com.project.recyclapp.modules.recycling_bins.material.controller;

import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import com.project.recyclapp.modules.recycling_bins.material.models.Material;
import com.project.recyclapp.modules.recycling_bins.material.services.interfaces.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("material")
public class MaterialController {
    @Autowired
    MaterialService materialService;

    @PostMapping("/register")
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materialService.registerMaterial(material));
    }

    @PostMapping("/update")
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialService.updateMaterial(material));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMaterial(@RequestBody Material material) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialService.deleteMaterial(material));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialService.getMaterialById(id));
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialService.getAllMaterials());
    }
}
