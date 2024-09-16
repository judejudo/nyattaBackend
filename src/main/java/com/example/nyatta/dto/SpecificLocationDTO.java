package com.example.nyatta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificLocationDTO {
    private Long id;
    private float latitude;
    private float longitude;
    private LocationDTO locationDTO;
}
