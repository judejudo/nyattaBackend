package com.example.nyatta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountyDTO {
    private Long id;
    private String countyName;
    private CountryDTO country;  // Assuming you want nested details, include CountryDTO
}
