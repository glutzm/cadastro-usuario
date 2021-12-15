package com.example.springbackend.security;

import com.example.springbackend.entities.User;
import com.example.springbackend.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUserDetailsImpl implements UserDetails {

    private final User user;

    public AppUserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role;
        switch (user.getRole().getName()) {
            case "Gerente" -> {
                role = Role.ADMIN;
            }
            default -> {
                role = Role.USER;
            }
        }

        return AuthorityUtils.createAuthorityList("ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive();
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getFullName() {
        return this.user.getName();
    }
}