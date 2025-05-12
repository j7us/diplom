package org.example.controller;

import org.example.entity.AuthGrantedAuthority;
import org.example.entity.Manager;

import java.util.Collections;
import java.util.List;

public class TestTemplate {

    public static Manager buildUserManagerWithoutPermit() {
        Manager manager = buildManagerForPermit();
        manager.setAuth(Collections.emptyList());

        return manager;
    }

    public static Manager buildManagerWithPermit() {
        Manager manager = buildManagerForPermit();
        manager.setAuth(List.of(buildAuthGrantedAuthority()));

        return manager;
    }

    private static Manager buildManagerForPermit() {
        Manager manager = new Manager();

        manager.setUsername("user");
        manager.setPassword("password");
        manager.setAccountNonExpired(true);
        manager.setAccountNonLocked(true);
        manager.setEnabled(true);

        return manager;
    }

    private static AuthGrantedAuthority buildAuthGrantedAuthority() {
        AuthGrantedAuthority authGrantedAuthority = new AuthGrantedAuthority();

        authGrantedAuthority.setAuthority("MANAGER");

        return authGrantedAuthority;
    }
}
