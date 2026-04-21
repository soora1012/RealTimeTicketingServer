package com.ticketing.config;

import com.ticketing.auth.jwt.JwtAuthenticationFilter;
import com.ticketing.auth.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider tokenProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/health",
                                "/actuator/health",
                                "/api/auth/**",
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(h -> h.frameOptions(f -> f.disable()))
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
