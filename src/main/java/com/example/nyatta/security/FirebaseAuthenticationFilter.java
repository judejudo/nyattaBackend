package com.example.nyatta.security;

import com.example.nyatta.entity.UserAccount;
import com.example.nyatta.service.UserAccountService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserAccountService userAccountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        FirebaseToken decodedToken = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // Remove "Bearer " from the token
            try {
                // Verify the token with Firebase
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            } catch (FirebaseAuthException e) {
                // Token verification failed
                System.out.println("Invalid Firebase token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (decodedToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String uid = decodedToken.getUid(); // Extract the UID from the token

            // You can use this UID to load the user from your database
            UserAccount userAccount = userAccountService.loadUserToContext(uid); // Example

            if (userAccount != null) {
                // Create a UsernamePasswordAuthenticationToken with the user details and authorities
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userAccount.getEmailAddress(),
                        null,
                        userAccount.getAuthorities()
                );

                // Set the authentication details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
