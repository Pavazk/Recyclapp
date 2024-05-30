package com.project.recyclapp.modules.recycling_bins.items.controller;

import com.project.recyclapp.modules.recycling_bins.items.models.Item;
import com.project.recyclapp.modules.recycling_bins.items.services.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("item")
public class ItemsController {

    @Autowired
    ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItem() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemService.getAllItems());
    }

}
