package com.example.nyatta.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_type_id")
    private PlaceType placeType;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "host_id")
    private UserAccount host;

    @ManyToOne
    @JoinColumn(name = "specific_location_id")
    private SpecificLocation specificLocation;

    private Double nightlyPrice;
    private String propertyName;
    private Integer numGuests;
    private Integer numBeds;
    private Integer numBedrooms;
    private Integer numBathrooms;
    private Boolean isGuestFavourite;
    private String description;
    private String addressLine1;
    private String addressLine2;

    @OneToMany(mappedBy = "property")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "property")
    private Set<UserReview> reviews;

    // Getters and Setters
}
