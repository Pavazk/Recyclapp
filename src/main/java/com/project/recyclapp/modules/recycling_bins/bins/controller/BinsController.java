package com.project.recyclapp.modules.recycling_bins.bins.controller;

import com.project.recyclapp.modules.recycling_bins.bins.models.Bin;
import com.project.recyclapp.modules.recycling_bins.bins.models.Color;
import com.project.recyclapp.modules.recycling_bins.bins.services.BinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bins")
public class BinsController {

    @Autowired
    private BinsService binsService;

    @PostMapping("/register")
    public ResponseEntity<Bin> createBin(@RequestBody Bin bin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(binsService.registerBin(bin));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Bin> updateBin(@RequestBody Bin bin, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(binsService.updateBin(bin, id));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteBin(@RequestBody Bin bin) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(binsService.deleteBin(bin));
    }

    @GetMapping
    public ResponseEntity<List<Bin>> getAllBins() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(binsService.getAllBins());
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Color>> getAllColors() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(binsService.getAllColors());
    }

}
