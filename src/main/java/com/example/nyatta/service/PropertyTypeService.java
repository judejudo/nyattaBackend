package com.example.nyatta.service;

import com.example.nyatta.dto.PropertyTypeDTO;

import java.util.List;

public interface PropertyTypeService {
    List<PropertyTypeDTO> getAllPropertyTypes();

    PropertyTypeDTO getPropertyTypeById(Long id);

    PropertyTypeDTO createPropertyType(PropertyTypeDTO propertyTypeDTO);

    PropertyTypeDTO updatePropertyType(Long id, PropertyTypeDTO propertyTypeDTO);

    void deletePropertyType(Long id);
}
