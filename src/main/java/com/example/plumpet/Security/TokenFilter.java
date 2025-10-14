package com.example.plumpet.Security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");
            
            // Only process if Authorization header is present and starts with "Bearer "
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String jwt = authorization.substring(7);
                
                if (jwt != null && !jwt.trim().isEmpty()) {
                    try {
                        String username = jwtCore.getnamefromJwt(jwt);
                        
                        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    } catch (ExpiredJwtException e) {
                        // Token expired - just log and continue without authentication
                        System.err.println("JWT token expired: " + e.getMessage());
                    } catch (Exception e) {
                        // Invalid token - just log and continue without authentication
                        System.err.println("Invalid JWT token: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            // Log the exception for debugging but don't block the request
            System.err.println("JWT processing error: " + e.getMessage());
        }
        
        // Always continue the filter chain
        filterChain.doFilter(request, response);
    }
}
