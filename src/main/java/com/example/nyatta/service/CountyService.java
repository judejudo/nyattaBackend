package com.example.nyatta.service;

import com.example.nyatta.dto.CountyDTO;

import java.util.List;

public interface CountyService {
    List<CountyDTO> getAllCounties();

    CountyDTO getCountyById(Long id);

    CountyDTO createCounty(CountyDTO countyDTO);

    CountyDTO updateCounty(Long id, CountyDTO countyDTO);

    void deleteCounty(Long id);
}
