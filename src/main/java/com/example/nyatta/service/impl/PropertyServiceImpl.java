package com.example.nyatta.service.impl;
import com.example.nyatta.entity.*;
import com.example.nyatta.exception.ResourceNotFoundException;
import com.example.nyatta.dto.*;
import com.example.nyatta.repository.*;
import com.example.nyatta.service.PropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private SpecificLocationRepository specificLocationRepository;

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
        propertyDTO.setSpecificlocationDTO(convertToLocationDTO(property.getSpecificLocation()));
        propertyDTO.setPropertyTypeDTO(convertToPropertyDTO(property.getPropertyType()));
        propertyDTO.setPlaceTypeDTO(convertToPlaceTypeDTO(property.getPlaceType()));

        // Convert the list of Images to ImagesDTO
        propertyDTO.setImagesDTO(convertToImagesDTO(property.getImages()));

        return propertyDTO;
    }

    // Corrected method to convert a list of Images to List<ImagesDTO>
    private List<ImagesDTO> convertToImagesDTO(List<Images> imagesList) {
        List<ImagesDTO> imagesDTOList = new ArrayList<>();

        for (Images images : imagesList) {
            ImagesDTO imagesDTO = new ImagesDTO();
            imagesDTO.setId(images.getId());
            imagesDTO.setImageURL(images.getImageURL());
            imagesDTO.setImageOrder(images.getImageOrder());

//            PropertyDTO propertyDTO = new PropertyDTO();
//            propertyDTO.setId(images.getProperty().getId());
//            imagesDTO.setPropertyDTO(propertyDTO);

            imagesDTOList.add(imagesDTO);
        }

        return imagesDTOList;
    }


    private SpecificLocationDTO convertToLocationDTO(SpecificLocation specificLocation) {
        SpecificLocationDTO specificLocationDTO = new SpecificLocationDTO();
        specificLocationDTO.setId(specificLocation.getId());
        specificLocationDTO.setLongitude(specificLocation.getLongitude());
        specificLocationDTO.setLatitude(specificLocation.getLatitude());

        return specificLocationDTO;
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
        Property property = PropertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));

        List<Images> images = ImageRepository.findByPropertyId(id);

        List<ImagesDTO> imagesDTO = images.stream().map(image -> {
            ImagesDTO imagesDTO1 = new ImagesDTO();
            imagesDTO1.setId(image.getId());
            imagesDTO1.setImageURL(image.getImageURL());
            imagesDTO1.setImageOrder(image.getImageOrder());

            // Assuming you want to set only the Property ID in ImagesDTO
            PropertyDTO propertyDTOForImage = new PropertyDTO();
            propertyDTOForImage.setId(property.getId());
            imagesDTO1.setPropertyDTO(propertyDTOForImage);

            return imagesDTO1;
        }).collect(Collectors.toList());

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setPropertyName(property.getPropertyName());
        propertyDTO.setNightlyPrice(property.getNightlyPrice());
        propertyDTO.setAddressLine1(property.getAddressLine1());
        propertyDTO.setAddressLine2(property.getAddressLine2());
        propertyDTO.setNumBeds(property.getNumBeds());
        propertyDTO.setNumBathrooms(property.getNumBathrooms());
        propertyDTO.setNumBedrooms(property.getNumBedrooms());
        propertyDTO.setNumGuests(property.getNumGuests());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setIsGuestFavourite(property.getIsGuestFavourite());
        propertyDTO.setImagesDTO(imagesDTO);  // Include the images

        // Handle other nested DTOs if necessary (PlaceTypeDTO, PropertyTypeDTO, SpecificLocationDTO, etc.)
        // Example:
        PlaceTypeDTO placeTypeDTO = new PlaceTypeDTO();
        placeTypeDTO.setId(property.getPlaceType().getId());
        placeTypeDTO.setTypeName(property.getPlaceType().getTypeName());
        propertyDTO.setPlaceTypeDTO(placeTypeDTO);

        PropertyTypeDTO propertyTypeDTO = new PropertyTypeDTO();
        propertyTypeDTO.setId(property.getPropertyType().getId());
        propertyTypeDTO.setTypeName(property.getPropertyType().getTypeName());
        propertyDTO.setPropertyTypeDTO(propertyTypeDTO);

        // Continue with other DTO mappings as needed...

        return propertyDTO;
    }


    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        // Convert DTO to Entity using BeanUtils
        Property property = new Property();
        BeanUtils.copyProperties(propertyDTO, property);

        // Handle nested DTOs manually
        if (propertyDTO.getSpecificlocationDTO() != null) {
            SpecificLocation specificLocation = new SpecificLocation();
            BeanUtils.copyProperties(propertyDTO.getSpecificlocationDTO(), specificLocation);
            property.setSpecificLocation(specificLocation);
        }

        if (propertyDTO.getPlaceTypeDTO() != null) {
            PlaceType placeType = new PlaceType();
            BeanUtils.copyProperties(propertyDTO.getPlaceTypeDTO(), placeType);
            property.setPlaceType(placeType);
        }

        if (propertyDTO.getPropertyTypeDTO() != null) {
            PropertyType propertyType = new PropertyType();
            BeanUtils.copyProperties(propertyDTO.getPropertyTypeDTO(), propertyType);
            property.setPropertyType(propertyType);
        }

        // Save the Property entity
        Property savedProperty = PropertyRepository.save(property);

        // Convert saved Entity back to DTO using BeanUtils
        PropertyDTO savedPropertyDTO = new PropertyDTO();
        BeanUtils.copyProperties(savedProperty, savedPropertyDTO);

        // Handle nested entities manually
        if (savedProperty.getSpecificLocation() != null) {
            LocationDTO locationDTO = new LocationDTO();
            BeanUtils.copyProperties(savedProperty.getSpecificLocation(), locationDTO);
            savedPropertyDTO.setSpecificlocationDTO(new SpecificLocationDTO());
        }

        if (savedProperty.getPlaceType() != null) {
            PlaceTypeDTO placeTypeDTO = new PlaceTypeDTO();
            BeanUtils.copyProperties(savedProperty.getPlaceType(), placeTypeDTO);
            savedPropertyDTO.setPlaceTypeDTO(placeTypeDTO);
        }

        if (savedProperty.getPropertyType() != null) {
            PropertyTypeDTO propertyTypeDTO = new PropertyTypeDTO();
            BeanUtils.copyProperties(savedProperty.getPropertyType(), propertyTypeDTO);
            savedPropertyDTO.setPropertyTypeDTO(propertyTypeDTO);
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
            PlaceType placeType = PlaceTypeRepository.findById(propertyDTO.getPlaceTypeDTO().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("PlaceType not found with id: " + propertyDTO.getPlaceTypeDTO().getId()));
            property.setPlaceType(placeType);

            // Update PropertyType
            PropertyType propertyType = PropertyTypeRepository.findById(propertyDTO.getPropertyTypeDTO().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("PropertyType not found with id: " + propertyDTO.getPropertyTypeDTO().getId()));
            property.setPropertyType(propertyType);

            // Update Location
            SpecificLocation  specificLocation = specificLocationRepository.findById(propertyDTO.getSpecificlocationDTO().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + propertyDTO.getSpecificlocationDTO().getId()));
            property.setSpecificLocation(specificLocation);

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
