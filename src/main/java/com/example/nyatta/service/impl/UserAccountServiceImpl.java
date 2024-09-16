package com.example.nyatta.service.impl;

import com.example.nyatta.dto.UserAccountDTO;
import com.example.nyatta.entity.UserAccount;
import com.example.nyatta.repository.UserAccountRepository;
import com.example.nyatta.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccountDTO convertToDTO(UserAccount userAccount) {
        return new UserAccountDTO(
                userAccount.getId(),
                userAccount.getFirstName(),
                userAccount.getLastName(),
                userAccount.getEmailAddress(),
                userAccount.getPassword(),
                userAccount.getJoinedDate(),
                userAccount.getDateHostStarted()
        );
    }

    private UserAccount convertToEntity(UserAccountDTO dto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(dto.getId());
        userAccount.setFirstName(dto.getFirstName());
        userAccount.setLastName(dto.getLastName());
        userAccount.setEmailAddress(dto.getEmailAddress());
        userAccount.setPassword(dto.getPassword());
        userAccount.setJoinedDate(dto.getJoinedDate());
        userAccount.setDateHostStarted(dto.getDateHostStarted());
        return userAccount;
    }

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
        UserAccount userAccount = convertToEntity(userAccountDTO);
        userAccount = userAccountRepository.save(userAccount);
        return convertToDTO(userAccount);
    }

    @Override
    public UserAccountDTO updateUserAccount(Long id, UserAccountDTO userAccountDTO) {
        return userAccountRepository.findById(id)
                .map(existingUserAccount -> {
                    existingUserAccount.setFirstName(userAccountDTO.getFirstName());
                    existingUserAccount.setLastName(userAccountDTO.getLastName());
                    existingUserAccount.setEmailAddress(userAccountDTO.getEmailAddress());
                    existingUserAccount.setPassword(userAccountDTO.getPassword());
                    existingUserAccount.setJoinedDate(userAccountDTO.getJoinedDate());
                    existingUserAccount.setDateHostStarted(userAccountDTO.getDateHostStarted());
                    return convertToDTO(userAccountRepository.save(existingUserAccount));
                })
                .orElse(null);
    }

    @Override
    public void deleteUserAccount(Long id) {
        userAccountRepository.deleteById(id);
    }
}
