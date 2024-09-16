package com.example.nyatta.service;

import com.example.nyatta.dto.UserAccountDTO;

import java.util.List;

public interface UserAccountService {
    List<UserAccountDTO> getAllUserAccounts();

    UserAccountDTO getUserAccountById(Long id);

    UserAccountDTO createUserAccount(UserAccountDTO userAccountDTO);

    UserAccountDTO updateUserAccount(Long id, UserAccountDTO userAccountDTO);

    void deleteUserAccount(Long id);
}
