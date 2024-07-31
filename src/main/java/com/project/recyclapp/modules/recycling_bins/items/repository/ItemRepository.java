package com.project.recyclapp.modules.recycling_bins.items.repository;

import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

}
