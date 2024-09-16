package com.example.nyatta.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
    private Long id;
    @NotNull(message = "Region information is required.")
    private RegionDTO regionDTO;

    @NotEmpty(message = "Country name is required.")
    private String countryName;
}
