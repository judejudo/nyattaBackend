package com.example.nyatta.service.impl;
import com.example.nyatta.entity.*;
import com.example.nyatta.exception.ResourceNotFoundException;
import com.example.nyatta.model.*;
import com.example.nyatta.repository.*;
import com.example.nyatta.service.PropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository PropertyRepository;

    @Autowired
    private ImageRepository ImageRepository;

    @Autowired
    private LocationRepository LocationRepository;

    @Autowired
    private PropertyTypeRepository PropertyTypeRepository;

    @Autowired
    private PlaceTypeRepository PlaceTypeRepository;

    @Override
    public List<PropertyDTO> getAllProperties() {
        return PropertyRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());

    }
    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setPropertyName(property.getPropertyName());
        propertyDTO.setNightlyPrice(property.getNightlyPrice());
        propertyDTO.setNumGuests(property.getNumGuests());
        propertyDTO.setNumBeds(property.getNumBeds());
        propertyDTO.setNumBedrooms(property.getNumBedrooms());
        propertyDTO.setNumBathrooms(property.getNumBathrooms());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setAddressLine1(property.getAddressLine1());
        propertyDTO.setAddressLine2(property.getAddressLine2());
        propertyDTO.setIsGuestFavourite(property.getIsGuestFavourite());

        // Convert related entities to DTOs
        propertyDTO.setLocation(convertToLocationDTO(property.getLocation()));
        propertyDTO.setPropertyType(convertToPropertyDTO(property.getPropertyType()));
        propertyDTO.setPlaceType(convertToPlaceTypeDTO(property.getPlaceType()));


        // Add other fields as needed

        return propertyDTO;
    }

    private LocationDTO convertToLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setLocationName(location.getLocationName());
        return locationDTO;
    }

    private PropertyTypeDTO convertToPropertyDTO(PropertyType propertyType) {
        PropertyTypeDTO propertyTypeDTO = new PropertyTypeDTO();
        propertyTypeDTO.setId(propertyType.getId());
        propertyTypeDTO.setTypeName(propertyType.getTypeName());
        return propertyTypeDTO;
    }

    private PlaceTypeDTO convertToPlaceTypeDTO(PlaceType placeType) {
        PlaceTypeDTO placeTypeDTO = new PlaceTypeDTO();
        placeTypeDTO.setId(placeType.getId());
        placeTypeDTO.setTypeName(placeType.getTypeName());
        return placeTypeDTO;
    }
    @Override
    public PropertyDTO getPropertyById(Long id) {
        Property property = PropertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        List<Images> images = ImageRepository.findByPropertyId(id);

        List<ImagesDTO> imagesDTO = images.stream().map(image -> new ImagesDTO(image.getImageURL(), image.getImageOrder())).collect(Collectors.toList());

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setPropertyName(property.getPropertyName());
        propertyDTO.setNightlyPrice(property.getNightlyPrice());
        propertyDTO.setAddressLine1(propertyDTO.getAddressLine1());
        propertyDTO.setAddressLine2(propertyDTO.getAddressLine2());
        propertyDTO.setNumBeds(property.getNumBeds());
        propertyDTO.setNumBathrooms(property.getNumBathrooms());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setImages(imagesDTO);  // Include the images

        return propertyDTO;
    }

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        // Convert DTO to Entity using BeanUtils
        Property property = new Property();
        BeanUtils.copyProperties(propertyDTO, property);

        // Handle nested DTOs manually
        if (propertyDTO.getLocation() != null) {
            Location location = new Location();
            BeanUtils.copyProperties(propertyDTO.getLocation(), location);
            property.setLocation(location);
        }

        if (propertyDTO.getPlaceType() != null) {
            PlaceType placeType = new PlaceType();
            BeanUtils.copyProperties(propertyDTO.getPlaceType(), placeType);
            property.setPlaceType(placeType);
        }

        if (propertyDTO.getPropertyType() != null) {
            PropertyType propertyType = new PropertyType();
            BeanUtils.copyProperties(propertyDTO.getPropertyType(), propertyType);
            property.setPropertyType(propertyType);
        }

        // Save the Property entity
        Property savedProperty = PropertyRepository.save(property);

        // Convert saved Entity back to DTO using BeanUtils
        PropertyDTO savedPropertyDTO = new PropertyDTO();
        BeanUtils.copyProperties(savedProperty, savedPropertyDTO);

        // Handle nested entities manually
        if (savedProperty.getLocation() != null) {
            LocationDTO locationDTO = new LocationDTO();
            BeanUtils.copyProperties(savedProperty.getLocation(), locationDTO);
            savedPropertyDTO.setLocation(locationDTO);
        }

        if (savedProperty.getPlaceType() != null) {
            PlaceTypeDTO placeTypeDTO = new PlaceTypeDTO();
            BeanUtils.copyProperties(savedProperty.getPlaceType(), placeTypeDTO);
            savedPropertyDTO.setPlaceType(placeTypeDTO);
        }

        if (savedProperty.getPropertyType() != null) {
            PropertyTypeDTO propertyTypeDTO = new PropertyTypeDTO();
            BeanUtils.copyProperties(savedProperty.getPropertyType(), propertyTypeDTO);
            savedPropertyDTO.setPropertyType(propertyTypeDTO);
        }

        return savedPropertyDTO;
    }


    @Override
    public Optional<PropertyDTO> updateProperty(Long id, PropertyDTO propertyDTO) {
        return PropertyRepository.findById(id).map(property -> {
            // Update basic property fields
            property.setPropertyName(propertyDTO.getPropertyName());
            property.setNightlyPrice(propertyDTO.getNightlyPrice());
            property.setNumGuests(propertyDTO.getNumGuests());
            property.setNumBeds(propertyDTO.getNumBeds());
            property.setNumBedrooms(propertyDTO.getNumBedrooms());
            property.setNumBathrooms(propertyDTO.getNumBathrooms());
            property.setDescription(propertyDTO.getDescription());
            property.setAddressLine1(propertyDTO.getAddressLine1());
            property.setAddressLine2(propertyDTO.getAddressLine2());
            property.setIsGuestFavourite(propertyDTO.getIsGuestFavourite());

            // Handle related entities

            // Update PlaceType
            PlaceType placeType = PlaceTypeRepository.findById(propertyDTO.getPlaceType().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("PlaceType not found with id: " + propertyDTO.getPlaceType().getId()));
            property.setPlaceType(placeType);

            // Update PropertyType
            PropertyType propertyType = PropertyTypeRepository.findById(propertyDTO.getPropertyType().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("PropertyType not found with id: " + propertyDTO.getPropertyType().getId()));
            property.setPropertyType(propertyType);

            // Update Location
            Location location = LocationRepository.findById(propertyDTO.getLocation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + propertyDTO.getLocation().getId()));
            property.setLocation(location);

            // Save the updated property
            Property updatedProperty = PropertyRepository.save(property);
            return convertToDTO(updatedProperty);
        });
    }

    @Override
    public void deleteProperty(Long id) {
        // Fetch the property by ID, or throw an exception if not found
        Property existingProperty = PropertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        // Delete associated images first
        ImageRepository.deleteByPropertyId(id);
        // Then delete the property itself
        PropertyRepository.delete(existingProperty);
    }
}
