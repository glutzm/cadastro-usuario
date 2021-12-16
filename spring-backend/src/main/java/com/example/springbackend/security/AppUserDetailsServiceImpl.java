package com.example.springbackend.security;

import com.example.springbackend.entities.User;
import com.example.springbackend.repositories.UserRepository;
import com.example.springbackend.util.CpfValidator;
import com.example.springbackend.util.PisValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PisValidator pisValidator;

    @Autowired
    private CpfValidator cpfValidator;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;

        if (pisValidator.validate(login)){
            user = userRepository.findByPis(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        } else if (cpfValidator.validate(login)) {
            user = userRepository.findByCpf(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        } else {
            user = userRepository.findByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        }

        return new AppUserDetailsImpl(user);
    }
}
