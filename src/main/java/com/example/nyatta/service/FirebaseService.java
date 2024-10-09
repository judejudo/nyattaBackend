package com.example.nyatta.service;

import com.example.nyatta.entity.UserAccount;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public interface FirebaseService {
    String verifyToken(String idToken) throws FirebaseAuthException;
    FirebaseToken getUserDetails(String idToken) throws FirebaseAuthException;
}

