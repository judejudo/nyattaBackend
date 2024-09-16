package com.example.nyatta.service.impl;

import com.example.nyatta.dto.PropertyTypeDTO;
import com.example.nyatta.entity.PropertyType;
import com.example.nyatta.repository.PropertyTypeRepository;
import com.example.nyatta.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyTypeServiceImpl implements PropertyTypeService {

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    private PropertyTypeDTO convertToDTO(PropertyType propertyType) {
        return new PropertyTypeDTO(
                propertyType.getId(),
                propertyType.getTypeName()
        );
    }

    private PropertyType convertToEntity(PropertyTypeDTO dto) {
        PropertyType propertyType = new PropertyType();
        propertyType.setId(dto.getId());
        propertyType.setTypeName(dto.getTypeName());
        return propertyType;
    }

    @Override
    public List<PropertyTypeDTO> getAllPropertyTypes() {
        return propertyTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyTypeDTO getPropertyTypeById(Long id) {
        return propertyTypeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public PropertyTypeDTO createPropertyType(PropertyTypeDTO propertyTypeDTO) {
        PropertyType propertyType = convertToEntity(propertyTypeDTO);
        propertyType = propertyTypeRepository.save(propertyType);
        return convertToDTO(propertyType);
    }

    @Override
    public PropertyTypeDTO updatePropertyType(Long id, PropertyTypeDTO propertyTypeDTO) {
        return propertyTypeRepository.findById(id)
                .map(existingPropertyType -> {
                    existingPropertyType.setTypeName(propertyTypeDTO.getTypeName());
                    return convertToDTO(propertyTypeRepository.save(existingPropertyType));
                })
                .orElse(null);
    }

    @Override
    public void deletePropertyType(Long id) {
        propertyTypeRepository.deleteById(id);
    }
}
