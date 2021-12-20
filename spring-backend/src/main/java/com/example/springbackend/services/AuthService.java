package com.example.springbackend.services;

import com.example.springbackend.api.dto.JwtResponse;
import com.example.springbackend.api.dto.UserLoginDTO;
import com.example.springbackend.auth.AuthenticatedUser;
import com.example.springbackend.entities.User;
import com.example.springbackend.repositories.UserRepository;
import com.example.springbackend.util.CpfValidator;
import com.example.springbackend.util.PisFieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User foundUser;

        if (PisFieldValidator.validate(login)){
            foundUser = userRepository.findByPis(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos."));
        } else if (CpfValidator.validate(login)) {
            foundUser = userRepository.findByCpf(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos."));
        } else {
            foundUser = userRepository.findByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos."));
        }

        return new AuthenticatedUser(foundUser);
    }

    public JwtResponse createJwtResponse(UserLoginDTO user) {
        String login = user.getLogin();
        String password = user.getPassword();

        var authentication = new UsernamePasswordAuthenticationToken(login, password);

        var authenticatedUser = authenticationManager.authenticate(authentication);

        String token = jwtService.generateToken(authenticatedUser);
        Date expiresAt = jwtService.getExpirationFromToken(token);

        return new JwtResponse(token, "Bearer", expiresAt);
    }
}
