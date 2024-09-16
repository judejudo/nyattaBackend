package com.example.nyatta.controller;

import com.example.nyatta.dto.PlaceTypeDTO;
import com.example.nyatta.service.PlaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placeTypes")
public class PlaceTypeController {

    @Autowired
    private PlaceTypeService placeTypeService;

    @GetMapping
    public List<PlaceTypeDTO> getAllPlaceTypes() {
        return placeTypeService.getAllPlaceTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceTypeDTO> getPlaceTypeById(@PathVariable Long id) {
        PlaceTypeDTO placeTypeDTO = placeTypeService.getPlaceTypeById(id);
        return placeTypeDTO != null ? ResponseEntity.ok(placeTypeDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PlaceTypeDTO createPlaceType(@RequestBody PlaceTypeDTO placeTypeDTO) {
        return placeTypeService.createPlaceType(placeTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceTypeDTO> updatePlaceType(@PathVariable Long id, @RequestBody PlaceTypeDTO placeTypeDTO) {
        PlaceTypeDTO updatedPlaceType = placeTypeService.updatePlaceType(id, placeTypeDTO);
        return updatedPlaceType != null ? ResponseEntity.ok(updatedPlaceType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaceType(@PathVariable Long id) {
        placeTypeService.deletePlaceType(id);
        return ResponseEntity.noContent().build();
    }
}
