package com.brapierre.cookbook.config;

import com.brapierre.cookbook.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔥 Disable CSRF for simplicity + Swagger
                .csrf(csrf -> csrf.disable())

                // 🔐 Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🌍 Public HTML pages
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // 🟢 Swagger (NO AUTH, NO REDIRECTS)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger/auth/**"
                        ).permitAll()

                        // 🟢 API (Swagger demo mode – allow access)
                        .requestMatchers("/api/**").permitAll()

                        // 🔒 Everything else needs login
                        .anyRequest().authenticated()
                )

                // 🧠 Browser login (Thymeleaf)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )

                // 🚪 Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )

                // 👤 UserDetailsService
                .userDetailsService(userDetailsService);

        return http.build();
    }
}
