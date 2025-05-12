package org.example.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class AuthGrantedAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
