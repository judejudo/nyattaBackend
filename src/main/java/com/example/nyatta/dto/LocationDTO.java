package com.example.nyatta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Long id;
    private CountyDTO county;  // Nested DTO for County
    private String locationName;
}
