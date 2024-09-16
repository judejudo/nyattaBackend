package com.example.nyatta.service.impl;

import com.example.nyatta.dto.CountyDTO;
import com.example.nyatta.dto.CountryDTO;
import com.example.nyatta.dto.RegionDTO;
import com.example.nyatta.entity.County;
import com.example.nyatta.entity.Country;
import com.example.nyatta.entity.Region;
import com.example.nyatta.repository.CountyRepository;
import com.example.nyatta.repository.RegionRepository;
import com.example.nyatta.service.CountyService;
import com.example.nyatta.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountyServiceImpl implements CountyService {

    @Autowired
    private CountyRepository countyRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryService countryService; // Autowired to fetch and convert Country entities

    private CountyDTO convertToDTO(County county) {

        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(county.getCountry().getId());
        countryDTO.setCountryName(county.getCountry().getCountryName());
        countryDTO.setRegionDTO(convertToRegionDTO(county.getCountry().getRegion()));
//
        CountyDTO countyDTO = new CountyDTO();
        countyDTO.setId(county.getId());
        countyDTO.setCountyName(county.getCountyName());
        countyDTO.setCountry(countryDTO);

        return countyDTO;
    }

    private RegionDTO convertToRegionDTO(Region region){
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(region.getId());
        regionDTO.setRegionName(region.getRegionName());

        return  regionDTO;
    }


    private County convertToEntity(CountyDTO dto) {
        // Fetch the Region entity based on the RegionDTO ID
        Region region = regionRepository.findById(dto.getCountry().getRegionDTO().getId()).orElse(null);

        // Create and populate the Country entity
        Country country = new Country();
        country.setId(dto.getCountry().getId());
        country.setCountryName(dto.getCountry().getCountryName());
        country.setRegion(region); // Set the Region entity

        // Create and populate the County entity
        County county = new County();
        county.setId(dto.getId());
        county.setCountyName(dto.getCountyName());
        county.setCountry(country);

        return county;
    }


    @Override
    public List<CountyDTO> getAllCounties() {
        return countyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountyDTO getCountyById(Long id) {
        return countyRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public CountyDTO createCounty(CountyDTO countyDTO) {
        County county = convertToEntity(countyDTO);
        county = countyRepository.save(county);
        return convertToDTO(county);
    }

    @Override
    public CountyDTO updateCounty(Long id, CountyDTO countyDTO) {
        return countyRepository.findById(id)
                .map(existingCounty -> {
                    existingCounty.setCountyName(countyDTO.getCountyName());
                    existingCounty.setCountry(convertToEntity(countyDTO).getCountry());
                    return convertToDTO(countyRepository.save(existingCounty));
                })
                .orElse(null);
    }

    @Override
    public void deleteCounty(Long id) {
        countyRepository.deleteById(id);
    }
}
