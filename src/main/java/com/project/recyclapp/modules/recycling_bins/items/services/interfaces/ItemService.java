package com.project.recyclapp.modules.recycling_bins.items.services.interfaces;

import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    Item registerItem(Item item);

    Item updateItem(Item item);

    Item getItemById(Integer id);

    String deleteItem(Item item);

    List<Item> getAllItems();

}
