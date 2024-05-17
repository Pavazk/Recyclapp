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

    @PostMapping("/register")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.registerItem(item));
    }

    @PostMapping("/update")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemService.updateItem(item));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemService.deleteItem(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemService.getItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItem() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemService.getAllItems());
    }

}
