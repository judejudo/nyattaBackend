package com.example.nyatta.controller;

import com.example.nyatta.dto.UserAccountDTO;
import com.example.nyatta.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userAccount")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping
    public List<UserAccountDTO> getAllUserAccounts() {
        return userAccountService.getAllUserAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> getUserAccountById(@PathVariable Long id) {
        UserAccountDTO userAccountDTO = userAccountService.getUserAccountById(id);
        return userAccountDTO != null ? ResponseEntity.ok(userAccountDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/signUp")
    public UserAccountDTO createUserAccount(@RequestBody UserAccountDTO userAccountDTO) {
        return userAccountService.createUserAccount(userAccountDTO);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UserAccountDTO> updateUserAccount(@PathVariable Long id, @RequestBody UserAccountDTO userAccountDTO) {
//        UserAccountDTO updatedUserAccount = userAccountService.updateUserAccount(id, userAccountDTO);
//        return updatedUserAccount != null ? ResponseEntity.ok(updatedUserAccount) : ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        userAccountService.deleteUserAccount(id);
        return ResponseEntity.noContent().build();
    }
}
