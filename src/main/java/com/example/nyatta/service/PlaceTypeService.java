package com.example.nyatta.service;

import com.example.nyatta.dto.PlaceTypeDTO;

import java.util.List;

public interface PlaceTypeService {
    List<PlaceTypeDTO> getAllPlaceTypes();

    PlaceTypeDTO getPlaceTypeById(Long id);

    PlaceTypeDTO createPlaceType(PlaceTypeDTO placeTypeDTO);

    PlaceTypeDTO updatePlaceType(Long id, PlaceTypeDTO placeTypeDTO);

    void deletePlaceType(Long id);
}
