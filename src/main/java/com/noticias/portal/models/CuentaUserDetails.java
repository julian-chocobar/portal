package com.noticias.portal.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CuentaUserDetails implements UserDetails {
    private final Cuenta cuenta;

    public CuentaUserDetails(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + cuenta.getRol().name()));
    }

    @Override
    public String getPassword() {
        return cuenta.getClave();
    }

    @Override
    public String getUsername() {
        return cuenta.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

