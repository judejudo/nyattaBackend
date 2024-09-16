package com.example.nyatta.controller;
import com.example.nyatta.dto.*;
import com.example.nyatta.service.ImageService;
import com.example.nyatta.service.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        PropertyDTO property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(property);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyDTO> createProperty(
            @RequestPart("propertyDTO") PropertyDTO propertyDTO,
            @RequestPart("images") List<MultipartFile> files,
            @RequestPart("imageOrders") List<Integer> imageOrders) {

        // Create property first
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO);
        Long propertyId = createdProperty.getId(); // Get the generated propertyId

        // Create images for the property
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            int imageOrder = imageOrders.get(i);
            imageService.createImage(file, propertyId, imageOrder);
        }

        return ResponseEntity.ok(createdProperty);
        }


    @PutMapping("/{id}")
    public ResponseEntity<Optional<PropertyDTO>> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
        Optional<PropertyDTO> updatedProperty = propertyService.updateProperty(id, propertyDTO);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}