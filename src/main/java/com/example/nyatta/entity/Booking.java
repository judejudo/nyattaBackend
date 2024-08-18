package com.example.nyatta.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "booking_status_id")
    private BookingStatus bookingStatus;

    @Temporal(TemporalType.DATE)
    private Date checkinDate;

    @Temporal(TemporalType.DATE)
    private Date checkoutDate;

    private Double nightlyPrice;
    private Double serviceFee;
    private Double cleaningFee;
    private Double totalPrice;

    // Getters and Setters
}
