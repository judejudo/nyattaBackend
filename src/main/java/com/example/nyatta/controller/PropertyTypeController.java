package com.example.nyatta.controller;

import com.example.nyatta.dto.PropertyTypeDTO;
import com.example.nyatta.entity.PropertyType;
import com.example.nyatta.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propertyTypes")
public class PropertyTypeController {
    @Autowired
    private PropertyTypeService propertyTypeService;

    @GetMapping
    public List<PropertyTypeDTO> getAllPropertyTypes() {
        return propertyTypeService.getAllPropertyTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyTypeDTO> getPropertyTypeById(@PathVariable Long id) {
        PropertyTypeDTO propertyTypeDTO = propertyTypeService.getPropertyTypeById(id);
        return propertyTypeDTO !=null ? ResponseEntity.ok(propertyTypeDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PropertyTypeDTO> createPropertyType(@RequestBody PropertyTypeDTO propertyTypeDTO) {
        return propertyTypeDTO !=null ? ResponseEntity.ok(propertyTypeService.createPropertyType(propertyTypeDTO)) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyTypeDTO> updatePropertyType(@PathVariable Long id, @RequestBody PropertyTypeDTO propertyTypeDTO) {
        PropertyTypeDTO updatedPropertyType = propertyTypeService.updatePropertyType(id,propertyTypeDTO);
        return updatedPropertyType != null ? ResponseEntity.ok(updatedPropertyType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropertyType(@PathVariable Long id){
        propertyTypeService.deletePropertyType(id);
        return ResponseEntity.noContent().build();
    }



}
