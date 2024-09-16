package com.example.nyatta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SpecificLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float latitude;

    private float longitude;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
