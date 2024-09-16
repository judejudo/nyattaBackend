package com.example.nyatta.service.impl;

import com.example.nyatta.dto.LocationDTO;
import com.example.nyatta.dto.SpecificLocationDTO;
import com.example.nyatta.entity.Location;
import com.example.nyatta.entity.Region;
import com.example.nyatta.entity.SpecificLocation;
import com.example.nyatta.repository.LocationRepository;
import com.example.nyatta.repository.SpecificLocationRepository;
import com.example.nyatta.service.SpecificLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecificLocationServiceImpl implements SpecificLocationService {

    @Autowired
    private SpecificLocationRepository specificLocationRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public SpecificLocationDTO createSpecificLocation(SpecificLocationDTO specificLocationDTO) {
        SpecificLocation specificLocation = convertToEntity(specificLocationDTO);
        specificLocation = specificLocationRepository.save(specificLocation);
        return convertToDTO(specificLocation);
    }

    @Override
    public SpecificLocationDTO getSpecificLocationById(Long id) {
        SpecificLocation specificLocation = specificLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specific Location not found"));
        return convertToDTO(specificLocation);
    }

    @Override
    public List<SpecificLocationDTO> getAllSpecificLocations() {
        return specificLocationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SpecificLocationDTO updateSpecificLocation(Long id, SpecificLocationDTO specificLocationDTO) {
        SpecificLocation existingSpecificLocation = specificLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specific Location not found"));

        // Update fields
        SpecificLocation updatedSpecificLocation = convertToEntity(specificLocationDTO);
        updatedSpecificLocation.setId(existingSpecificLocation.getId());

        updatedSpecificLocation = specificLocationRepository.save(updatedSpecificLocation);
        return convertToDTO(updatedSpecificLocation);
    }

    private SpecificLocation convertToEntity(SpecificLocationDTO specificLocationDTO) {
        SpecificLocation specificLocation = new SpecificLocation();
        Location location = locationRepository.findById(specificLocationDTO.getLocationDTO().getId()).orElse(null);
        specificLocation.setLatitude(specificLocationDTO.getLatitude());
        specificLocation.setLongitude(specificLocationDTO.getLongitude());

        specificLocation.setLocation(location);
        return specificLocation;
    }

    @Override
    public void deleteSpecificLocation(Long id) {
        specificLocationRepository.deleteById(id);
    }

    private SpecificLocationDTO convertToDTO(SpecificLocation specificLocation) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(specificLocation.getLocation().getId());
        locationDTO.setLocationName(specificLocation.getLocation().getLocationName());

        SpecificLocationDTO specificLocationDTO = new SpecificLocationDTO();
        specificLocationDTO.setId(specificLocation.getId());
        specificLocationDTO.setLatitude(specificLocation.getLatitude());
        specificLocationDTO.setLongitude(specificLocation.getLongitude());
        specificLocationDTO.setLocationDTO(locationDTO);

        return specificLocationDTO;
    }


}
