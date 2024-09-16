package com.example.nyatta.service.impl;

import com.example.nyatta.dto.CountryDTO;
import com.example.nyatta.dto.RegionDTO;
import com.example.nyatta.entity.Country;
import com.example.nyatta.entity.Region;
import com.example.nyatta.repository.CountryRepository;
import com.example.nyatta.repository.RegionRepository;
import com.example.nyatta.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;
    private CountryDTO convertToDTO(Country country) {
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

    private Country convertToEntity(CountryDTO dto) {
        Country country = new Country();
        if (dto.getRegionDTO() != null) {
            Region region = regionRepository.findById(dto.getRegionDTO().getId()).orElse(null);
            country.setRegion(region);
        } else {
            // Handle the case where RegionDTO is null
            throw new IllegalArgumentException("Region information is required.");
        }
        country.setCountryName(dto.getCountryName());
        return country;
    }


    @Override
    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO getCountryById(Long id) {
        return countryRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public CountryDTO createCountry(CountryDTO countryDTO) {
        Country country = convertToEntity(countryDTO);
        country = countryRepository.save(country);
        return convertToDTO(country);
    }

    @Override
    public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
        return countryRepository.findById(id)
                .map(existingCountry -> {
                    Region region = regionRepository.findById(countryDTO.getRegionDTO().getId()).orElse(null);
                    existingCountry.setRegion(region);
                    existingCountry.setCountryName(countryDTO.getCountryName());
                    return convertToDTO(countryRepository.save(existingCountry));
                })
                .orElse(null);
    }

    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
