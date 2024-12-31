package com.financeTracker.PersonelFinanceTracker.service;

import com.financeTracker.PersonelFinanceTracker.dto.UserDTO;
import com.financeTracker.PersonelFinanceTracker.entity.User;

import java.util.Optional;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    UserDTO getUserById(Long userId);
    UserDTO getUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
} 