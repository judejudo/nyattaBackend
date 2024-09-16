package com.example.nyatta.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {

    // Define fields
    private Long id;
    private String propertyName;
    private Double nightlyPrice;

    private Integer numGuests;
    private Integer numBeds;
    private Integer numBedrooms;
    private Integer numBathrooms;
    private Boolean isGuestFavourite;
    private String description;
    private String addressLine1;
    private String addressLine2;

    private SpecificLocationDTO specificlocationDTO;  // Nested DTO for location
    private PlaceTypeDTO placeTypeDTO;  // Nested DTO for place type
    private PropertyTypeDTO propertyTypeDTO;  // Nested DTO for property type
    private List<ImagesDTO> imagesDTO; // Nested DTO for images
}