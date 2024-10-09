package com.example.nyatta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String confirmPassword;
    private String firebaseUid;
    private Date joinedDate;
    private Date dateHostStarted;
}
