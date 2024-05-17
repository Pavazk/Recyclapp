package com.project.recyclapp.modules.recycling_bins.items.services.implement;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.commons.messages.ErrorMessage;
import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import com.project.recyclapp.modules.recycling_bins.items.repository.ItemRepository;
import com.project.recyclapp.modules.recycling_bins.items.services.interfaces.ItemService;
import com.project.recyclapp.modules.recycling_bins.material.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImplements implements ItemService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MaterialRepository materialRepository;

    @Override
    public Item registerItem(Item item) {
        if (item == null || item.getName() == null || item.getMaterial() == null || item.getMaterial().getId() == null || item.getSubitem() == null || item.getSubitem().getId() == null) {
            throw Utils.noDataValid();
        }
        if (materialRepository.findById(item.getMaterial().getId()).isEmpty()) {
            throw new CustomException(ErrorMessage.MATERIAL_NO_EXISTS.getMessage());
        }
        if (itemRepository.findById(item.getSubitem().getId()).isEmpty()) {
            throw new CustomException(ErrorMessage.ITEM_NO_EXISTS.getMessage());
        }
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        if (item == null || item.getName() == null || item.getMaterial() == null || item.getMaterial().getId() == null || item.getSubitem() == null || item.getSubitem().getId() == null) {
            throw Utils.noDataValid();
        }
        Optional<Item> oldItem = itemRepository.findById(item.getId());
        if (oldItem.isEmpty()) {
            throw Utils.noDataValid();
        }
        if (itemRepository.findById(item.getSubitem().getId()).isPresent()) {
            oldItem.get().setSubitem(item.getSubitem());
        }
        if (Utils.isNotNullOrEmptyWithTrim(item.getName())) {
            oldItem.get().setName(item.getName());
        }
        return itemRepository.save(oldItem.get());
    }

    @Override
    public Item getItemById(Integer id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new CustomException(ErrorMessage.ITEM_NO_EXISTS.getMessage());
        }
        return item.get();
    }

    @Override
    public String deleteItem(Item item) {
        itemRepository.delete(item);
        return "Item deleted";
    }

    @Override
    public List<Item> getAllItems() {
        return (List<Item>) itemRepository.findAll();
    }
}
