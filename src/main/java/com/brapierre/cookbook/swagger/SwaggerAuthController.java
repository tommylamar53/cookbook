package com.brapierre.cookbook.swagger;

import com.brapierre.cookbook.dto.LoginRequest;
import com.brapierre.cookbook.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/swagger/auth")
@Tag(name = "Swagger Demo Auth")
public class SwaggerAuthController {

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterRequest request) {
        return Map.of(
                "message", "Swagger demo user registered",
                "email", request.getEmail()
        );
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        return Map.of(
                "message", "Swagger demo login successful",
                "token", "swagger-demo-token"
        );
    }
}
