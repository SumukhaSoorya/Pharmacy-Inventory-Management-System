package com.pims.pims.config;

import com.pims.pims.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

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

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AUTHENTICATION PROVIDER
    @Bean
    public DaoAuthenticationProvider authProvider() {

        DaoAuthenticationProvider auth =
                new DaoAuthenticationProvider();

        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    // SECURITY RULES
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                // We keep CSRF disabled for now because your forms are simple.
                // Later, after project is stable, we can enable CSRF properly.
                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authProvider())

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC PAGES
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/register/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**"
                        ).permitAll()

                        // FACTORY ONLY
                        .requestMatchers(
                                "/factory/**"
                        ).hasAuthority("ROLE_FACTORY")

                        // ADMIN / PHARMACIST ONLY
                        .requestMatchers(
                                "/dashboard",
                                "/medicines/**",
                                "/stock/**",
                                "/billing/**",
                                "/purchase-orders/**",
                                "/reports/**",
                                "/suppliers/**"
                        ).hasAuthority("ROLE_ADMIN")

                        // ANY OTHER PAGE NEEDS LOGIN
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .successHandler((request, response, authentication) -> {

                            boolean isFactory =
                                    authentication.getAuthorities()
                                            .stream()
                                            .anyMatch(a ->
                                                    a.getAuthority()
                                                            .equals("ROLE_FACTORY")
                                            );

                            if (isFactory) {

                                response.sendRedirect(
                                        "/factory/dashboard"
                                );

                            } else {

                                response.sendRedirect(
                                        "/dashboard"
                                );
                            }
                        })

                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}