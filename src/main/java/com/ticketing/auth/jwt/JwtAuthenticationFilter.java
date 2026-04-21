package com.ticketing.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = resolveBearer(request);
        if (token != null) {
            try {
                Claims claims = tokenProvider.parse(token).getBody();
                String userId = claims.getSubject();
                String role = String.valueOf(claims.get("role"));

                var auth = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }

    private String resolveBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header)) return null;
        if (!header.startsWith("Bearer ")) return null;
        return header.substring(7);
    }
}
