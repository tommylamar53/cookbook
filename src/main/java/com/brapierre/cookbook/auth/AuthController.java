package com.brapierre.cookbook.auth;

import com.brapierre.cookbook.dto.LoginRequest;
import com.brapierre.cookbook.dto.RegisterRequest;
import com.brapierre.cookbook.entity.User;
import com.brapierre.cookbook.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepo,
                          PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 SWAGGER DEMO REGISTER
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest req) {

        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");

        return userRepo.save(user);
    }

    // 🔹 SWAGGER DEMO LOGIN (NO JWT)
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return "Login successful (Swagger demo)";
    }
}
