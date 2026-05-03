package com.brapierre.cookbook.service;

import com.brapierre.cookbook.entity.User;
import com.brapierre.cookbook.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepo,
                                   PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Used by RegisterController
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // default role
        userRepo.save(user);
    }
}
