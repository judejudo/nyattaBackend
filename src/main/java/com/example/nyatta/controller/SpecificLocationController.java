package com.example.nyatta.controller;

import com.example.nyatta.dto.SpecificLocationDTO;
import com.example.nyatta.service.SpecificLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specific-locations")
public class SpecificLocationController {

    @Autowired
    private SpecificLocationService specificLocationService;

    @PostMapping
    public ResponseEntity<SpecificLocationDTO> createSpecificLocation(@RequestBody SpecificLocationDTO specificLocationDTO) {
        return ResponseEntity.ok(specificLocationService.createSpecificLocation(specificLocationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificLocationDTO> getSpecificLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(specificLocationService.getSpecificLocationById(id));
    }

    @GetMapping
    public ResponseEntity<List<SpecificLocationDTO>> getAllSpecificLocations() {
        return ResponseEntity.ok(specificLocationService.getAllSpecificLocations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecificLocationDTO> updateSpecificLocation(@PathVariable Long id, @RequestBody SpecificLocationDTO specificLocationDTO) {
        return ResponseEntity.ok(specificLocationService.updateSpecificLocation(id, specificLocationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecificLocation(@PathVariable Long id) {
        specificLocationService.deleteSpecificLocation(id);
        return ResponseEntity.noContent().build();
    }
}
