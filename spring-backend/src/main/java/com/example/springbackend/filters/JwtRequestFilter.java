package com.example.springbackend.filters;

import com.example.springbackend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String TOKEN_TYPE = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = "";
        String login = "";

        String authorizationHeader = request.getHeader("Authorization");

        if (isToeknPresent(authorizationHeader)) {
            token = authorizationHeader.substring(TOKEN_TYPE.length());
            login = jwtService.getLoginFromToken(token);
        }

        if (isLoginNotInContext(login)) {
            addLoginInContext(request, login, token);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isToeknPresent(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE);
    }

    private boolean isLoginNotInContext(String login) {
        return !login.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void addLoginInContext(HttpServletRequest request, String login, String token) {
        var userDetails = userDetailsService.loadUserByUsername(login);

        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
