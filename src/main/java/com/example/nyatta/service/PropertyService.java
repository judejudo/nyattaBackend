package com.example.nyatta.service;

import com.example.nyatta.dto.PropertyDTO;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    // Get a list of all properties
    List<PropertyDTO> getAllProperties();

    // Get a single property by its ID
    PropertyDTO getPropertyById(Long id);

    // Create a new property
    PropertyDTO createProperty(PropertyDTO propertyDTO);

    // Update an existing property by its ID
    Optional<PropertyDTO> updateProperty(Long id, PropertyDTO propertyDTO);
    void deleteProperty(Long id);
}
