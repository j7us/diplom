package org.example.service.security;

import org.example.repository.ManagerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ManagerAuthService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    public ManagerAuthService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return managerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Не найден пользователь с именем" + username));
    }
}
