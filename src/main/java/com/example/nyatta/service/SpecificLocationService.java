package com.example.nyatta.service;

import com.example.nyatta.dto.SpecificLocationDTO;
import java.util.List;

public interface SpecificLocationService {
    SpecificLocationDTO createSpecificLocation(SpecificLocationDTO specificLocationDTO);
    SpecificLocationDTO getSpecificLocationById(Long id);
    List<SpecificLocationDTO> getAllSpecificLocations();
    SpecificLocationDTO updateSpecificLocation(Long id, SpecificLocationDTO specificLocationDTO);
    void deleteSpecificLocation(Long id);
}
