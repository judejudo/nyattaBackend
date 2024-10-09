package com.example.nyatta.service.impl;

import com.example.nyatta.dto.UserAccountDTO;
import com.example.nyatta.entity.UserAccount;
import com.example.nyatta.repository.UserAccountRepository;
import com.example.nyatta.service.UserAccountService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord; // Import Firebase UserRecord
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private FirebaseServiceImpl firebaseService; // Autowire FirebaseServiceImpl

    @Override
    public UserAccount loadUserToContext(String uid) throws UsernameNotFoundException {
        // Load the user from the database using Firebase UID
        UserAccount user = userAccountRepository.findByFirebaseUid(uid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with UID: " + uid));

        // Convert UserAccounts entity to Spring Security's UserDetails
        return new UserAccount(user.getEmailAddress(), "", user.getAuthorities());
    }



    // Convert entity to DTO
    private UserAccountDTO convertToDTO(UserAccount userAccount) {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(userAccount.getId());
        userAccountDTO.setFirstName(userAccount.getFirstName());
        userAccountDTO.setLastName(userAccount.getLastName());
        userAccountDTO.setEmailAddress(userAccount.getEmailAddress());
        userAccountDTO.setJoinedDate(userAccount.getJoinedDate());
        userAccountDTO.setDateHostStarted(userAccount.getDateHostStarted());


        return userAccountDTO;
    }

    // Convert DTO to entity
//    private UserAccount convertToEntity(UserAccountDTO dto) {
//        UserAccount userAccount = new UserAccount();
//        userAccount.setFirstName(dto.getFirstName());
//        userAccount.setLastName(dto.getLastName());
//        userAccount.setEmailAddress(dto.getEmailAddress());
//        // No password to set
//        userAccount.setJoinedDate(dto.getJoinedDate());
//        userAccount.setDateHostStarted(dto.getDateHostStarted());
//        return userAccount;
//    }

    @Override
    public List<UserAccountDTO> getAllUserAccounts() {
        return userAccountRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserAccountDTO getUserAccountById(Long id) {
        return userAccountRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public UserAccountDTO createUserAccount(UserAccountDTO userAccountDTO) {
        try {



            // Create UserAccount entity
            UserAccount userAccount = new UserAccount();
            userAccount.setFirstName(userAccountDTO.getFirstName());
            userAccount.setLastName(userAccountDTO.getLastName());
            userAccount.setEmailAddress(userAccountDTO.getEmailAddress());
            userAccount.setJoinedDate(new Date()); // Set current date
            userAccount.setFirebaseUid(userAccountDTO.getFirebaseUid());
            userAccount.setDateHostStarted(new Date());

            return convertToDTO(userAccountRepository.save(userAccount));
        } catch (Exception e) {
            // Handle exceptions (e.g., user already exists)
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public UserAccountDTO updateUserAccount(Long id, UserAccountDTO userAccountDTO) {
//        return userAccountRepository.findById(id)
//                .map(existingUserAccount -> {
//                    existingUserAccount.setFirstName(userAccountDTO.getFirstName());
//                    existingUserAccount.setLastName(userAccountDTO.getLastName());
//                    existingUserAccount.setEmailAddress(userAccountDTO.getEmailAddress());
//                    // No password update needed
//                    return convertToDTO(userAccountRepository.save(existingUserAccount));
//                })
//                .orElse(null);
//    }

    @Override
    public void deleteUserAccount(Long id) {
        // Optionally, delete the user from Firebase as well
        UserAccount userAccount = userAccountRepository.findById(id).orElse(null);
        if (userAccount != null) {
            try {
                FirebaseAuth.getInstance().deleteUser(userAccount.getFirebaseUid()); // Delete from Firebase
                userAccountRepository.deleteById(id); // Delete from your database
            } catch (Exception e) {
                // Handle exceptions during deletion
                e.printStackTrace();
            }
        }
    }
}
