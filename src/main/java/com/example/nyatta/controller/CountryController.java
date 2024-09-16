package com.example.nyatta.controller;

import com.example.nyatta.dto.CountryDTO;
import com.example.nyatta.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable Long id) {
        CountryDTO countryDTO = countryService.getCountryById(id);
        return countryDTO != null ? ResponseEntity.ok(countryDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CountryDTO createCountry(@RequestBody CountryDTO countryDTO) {
        return countryService.createCountry(countryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable Long id, @RequestBody CountryDTO countryDTO) {
        CountryDTO updatedCountry = countryService.updateCountry(id, countryDTO);
        return updatedCountry != null ? ResponseEntity.ok(updatedCountry) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}
