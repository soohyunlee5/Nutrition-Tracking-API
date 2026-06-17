package com.soohyun.nutrition_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructor injection - Spring provides JwtService and UserDetailsService automatically
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Grab the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // If no token or doesn't start with "Bearer ", skip and let request through
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Strip "Bearer " prefix to get the raw token
        final String token = authHeader.substring(7);

        // Extract the email from inside the token
        final String email = jwtService.extractEmail(token);

        // If email exists and user isn't already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the full user details from the database using the email
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Validate the token against the user
            if (jwtService.isTokenValid(token, userDetails.getUsername())) {

                // Create an authentication object with user's details and permissions
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                // Attach request details to the authentication object
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Tell Spring user is authenticated for this request
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue to the next filter or controller
        filterChain.doFilter(request, response);
    }
}