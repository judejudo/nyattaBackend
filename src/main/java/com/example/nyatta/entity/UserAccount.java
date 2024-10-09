package com.example.nyatta.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String role;

    // Store Firebase UID instead of password
    private String firebaseUid;

    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    @Temporal(TemporalType.DATE)
    private Date dateHostStarted;

    public UserAccount(String emailAddress, String s, Collection<? extends GrantedAuthority> authorities) {
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming you only have one role per user for now
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    // Getters and Setters
}
