package com.example.nyatta.service.impl;

import com.example.nyatta.entity.UserAccount;
import com.example.nyatta.repository.UserAccountRepository;
import com.example.nyatta.service.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public String verifyToken(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }
//
//
//
    @Override
    public FirebaseToken getUserDetails(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }
}
