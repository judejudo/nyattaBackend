package com.example.nyatta.service;

import com.example.nyatta.dto.CountryDTO;

import java.util.List;

public interface CountryService {
    List<CountryDTO> getAllCountries();

    CountryDTO getCountryById(Long id);

    CountryDTO createCountry(CountryDTO countryDTO);

    CountryDTO updateCountry(Long id, CountryDTO countryDTO);

    void deleteCountry(Long id);
}
