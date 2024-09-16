package com.example.nyatta.controller;

import com.example.nyatta.dto.RegionDTO;
import com.example.nyatta.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<RegionDTO> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getRegionById(@PathVariable Long id) {
        RegionDTO regionDTO = regionService.getRegionById(id);
        return regionDTO != null ? ResponseEntity.ok(regionDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public RegionDTO createRegion(@RequestBody RegionDTO regionDTO) {
        return regionService.createRegion(regionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> updateRegion(@PathVariable Long id, @RequestBody RegionDTO regionDTO) {
        RegionDTO updatedRegion = regionService.updateRegion(id, regionDTO);
        return updatedRegion != null ? ResponseEntity.ok(updatedRegion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }
}
