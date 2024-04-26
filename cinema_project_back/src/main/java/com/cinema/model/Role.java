package com.cinema.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * Role table representation from database
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    @Column( length = 60, unique = true )
    private String role;

    public Role() {
    }

    public Role( String role ) {
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return role.equals(role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

}
