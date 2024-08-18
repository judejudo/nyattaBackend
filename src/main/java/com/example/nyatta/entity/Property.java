package com.example.nyatta.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "place_type_id")
    private PlaceType placeType;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private UserAccount host;

    @OneToOne
    @JoinColumn(name = "specific_location")
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
