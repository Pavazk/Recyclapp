package com.project.recyclapp.modules.recycling_bins.bins.repository;

import com.project.recyclapp.modules.recycling_bins.bins.models.Bin;
import org.springframework.data.repository.CrudRepository;

public interface BinsRepository extends CrudRepository<Bin, Integer> {
}
