package com.example.nyatta.controller;

import com.example.nyatta.dto.LocationDTO;
import com.example.nyatta.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        LocationDTO locationDTO = locationService.getLocationById(id);
        return locationDTO != null ? ResponseEntity.ok(locationDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public LocationDTO createLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.createLocation(locationDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(id, locationDTO);
        return updatedLocation != null ? ResponseEntity.ok(updatedLocation) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
