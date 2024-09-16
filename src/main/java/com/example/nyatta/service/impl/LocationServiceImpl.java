package com.example.nyatta.service.impl;

import com.example.nyatta.dto.CountryDTO;
import com.example.nyatta.dto.CountyDTO;
import com.example.nyatta.dto.LocationDTO;
import com.example.nyatta.dto.RegionDTO;
import com.example.nyatta.entity.Country;
import com.example.nyatta.entity.County;
import com.example.nyatta.entity.Location;
import com.example.nyatta.entity.Region;
import com.example.nyatta.repository.CountyRepository;
import com.example.nyatta.repository.LocationRepository;
import com.example.nyatta.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CountyRepository countyRepository;

    private LocationDTO convertToDTO(Location location) {


        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());

        CountyDTO countyDTO = new CountyDTO();
        countyDTO.setId(location.getCounty().getId());
        countyDTO.setCountyName(location.getCounty().getCountyName());
        countyDTO.setCountry(convertToCountryDTO(location.getCounty().getCountry()));
        // If CountyDTO has more nested attributes, handle them here

        dto.setCounty(countyDTO);
        dto.setLocationName(location.getLocationName());


        return dto;
    }

    private CountryDTO convertToCountryDTO(Country country) {
        CountryDTO dto = new CountryDTO();
        dto.setRegionDTO(convertToRegionDTO(country.getRegion()));
        dto.setCountryName(country.getCountryName());
        return dto;
    }

    private RegionDTO convertToRegionDTO(Region region) {
        if (region == null) {
            return null; // Handle cases where the region is null
        }
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(region.getId());
        regionDTO.setRegionName(region.getRegionName());
        return regionDTO;
    }


    private Location convertToEntity(LocationDTO dto) {
        Location location = new Location();
        location.setId(dto.getId());
        County county = countyRepository.findById(dto.getCounty().getId()).orElse(null);
        location.setCounty(county);
        location.setLocationName(dto.getLocationName());
        return location;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        return locationRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = convertToEntity(locationDTO);
        location = locationRepository.save(location);
        return convertToDTO(location);
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        return locationRepository.findById(id)
                .map(existingLocation -> {
                    County county = countyRepository.findById(locationDTO.getCounty().getId()).orElse(null);
                    existingLocation.setCounty(county);
                    existingLocation.setLocationName(locationDTO.getLocationName());
                    return convertToDTO(locationRepository.save(existingLocation));
                })
                .orElse(null);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
