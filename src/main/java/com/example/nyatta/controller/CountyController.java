package com.example.nyatta.controller;

import com.example.nyatta.dto.CountyDTO;
import com.example.nyatta.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counties")
public class CountyController {

    @Autowired
    private CountyService countyService;

    @GetMapping
    public List<CountyDTO> getAllCounties() {
        return countyService.getAllCounties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountyDTO> getCountyById(@PathVariable Long id) {
        CountyDTO countyDTO = countyService.getCountyById(id);
        return countyDTO != null ? ResponseEntity.ok(countyDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CountyDTO createCounty(@RequestBody CountyDTO countyDTO) {
        return countyService.createCounty(countyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountyDTO> updateCounty(@PathVariable Long id, @RequestBody CountyDTO countyDTO) {
        CountyDTO updatedCounty = countyService.updateCounty(id, countyDTO);
        return updatedCounty != null ? ResponseEntity.ok(updatedCounty) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        countyService.deleteCounty(id);
        return ResponseEntity.noContent().build();
    }
}
