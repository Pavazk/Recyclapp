package com.project.recyclapp.modules.recycling_bins.material.repository;

import com.project.recyclapp.modules.recycling_bins.material.models.Material;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Integer> {
}
