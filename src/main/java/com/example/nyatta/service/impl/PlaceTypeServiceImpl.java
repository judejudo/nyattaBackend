package com.example.nyatta.service.impl;

import com.example.nyatta.dto.PlaceTypeDTO;
import com.example.nyatta.entity.PlaceType;
import com.example.nyatta.repository.PlaceTypeRepository;
import com.example.nyatta.service.PlaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceTypeServiceImpl implements PlaceTypeService {

    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    private PlaceTypeDTO convertToDTO(PlaceType placeType) {
        return new PlaceTypeDTO(
                placeType.getId(),
                placeType.getTypeName()
        );
    }

    private PlaceType convertToEntity(PlaceTypeDTO dto) {
        PlaceType placeType = new PlaceType();
        placeType.setId(dto.getId());
        placeType.setTypeName(dto.getTypeName());
        return placeType;
    }

    @Override
    public List<PlaceTypeDTO> getAllPlaceTypes() {
        return placeTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceTypeDTO getPlaceTypeById(Long id) {
        return placeTypeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public PlaceTypeDTO createPlaceType(PlaceTypeDTO placeTypeDTO) {
        PlaceType placeType = convertToEntity(placeTypeDTO);
        placeType = placeTypeRepository.save(placeType);
        return convertToDTO(placeType);
    }

    @Override
    public PlaceTypeDTO updatePlaceType(Long id, PlaceTypeDTO placeTypeDTO) {
        return placeTypeRepository.findById(id)
                .map(existingPlaceType -> {
                    existingPlaceType.setTypeName(placeTypeDTO.getTypeName());
                    return convertToDTO(placeTypeRepository.save(existingPlaceType));
                })
                .orElse(null);
    }

    @Override
    public void deletePlaceType(Long id) {
        placeTypeRepository.deleteById(id);
    }
}
