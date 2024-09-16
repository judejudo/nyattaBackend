package com.example.nyatta.service;

import com.example.nyatta.dto.RegionDTO;

import java.util.List;

public interface RegionService {

    List<RegionDTO> getAllRegions();

    RegionDTO getRegionById(Long id);

    RegionDTO createRegion(RegionDTO regionDTO);

    RegionDTO updateRegion(Long id, RegionDTO regionDTO);

    void deleteRegion(Long id);
}
