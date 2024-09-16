package com.example.nyatta.service.impl;

import com.example.nyatta.dto.RegionDTO;
import com.example.nyatta.entity.Region;
import com.example.nyatta.repository.RegionRepository;
import com.example.nyatta.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    private RegionDTO convertToDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setRegionName(region.getRegionName());
        return dto;
    }

    private Region convertToEntity(RegionDTO dto) {
        Region region = new Region();
        region.setId(dto.getId());
        region.setRegionName(dto.getRegionName());
        return region;
    }

    @Override
    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegionDTO getRegionById(Long id) {
        return regionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public RegionDTO createRegion(RegionDTO regionDTO) {
        Region region = convertToEntity(regionDTO);
        region = regionRepository.save(region);
        return convertToDTO(region);
    }

    @Override
    public RegionDTO updateRegion(Long id, RegionDTO regionDTO) {
        return regionRepository.findById(id)
                .map(existingRegion -> {
                    existingRegion.setRegionName(regionDTO.getRegionName());
                    return convertToDTO(regionRepository.save(existingRegion));
                })
                .orElse(null);
    }

    @Override
    public void deleteRegion(Long id) {
        regionRepository.deleteById(id);
    }
}
