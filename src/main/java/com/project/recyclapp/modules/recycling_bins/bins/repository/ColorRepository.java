package com.project.recyclapp.modules.recycling_bins.bins.repository;

import com.project.recyclapp.modules.recycling_bins.bins.models.Color;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepository extends CrudRepository<Color, Integer> {
}
