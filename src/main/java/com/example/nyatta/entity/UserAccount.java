package com.example.nyatta.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    @Temporal(TemporalType.DATE)
    private Date dateHostStarted;

    // Getters and Setters
}
