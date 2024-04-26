package com.cinema.model;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * RegisteredUsers table representation from database
 */
@Entity
@Table(name = "registered_users")
public class RegisteredUser implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "registered_users_generator" )
    @SequenceGenerator( name = "registered_users_generator", sequenceName = "registered_users_seq", allocationSize = 1)
    private Long id;
    @Column(length = 60)
    private String fname;
    @Column(length = 60)
    private String lname;
    @Column(length = 60, unique = true)
    private String mail;
    @Column(length = 60, unique = true)
    private String login;
    @Column(length = 60)
    private String password;
    @Column(length = 12, unique = true)
    private String phone;
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
            name = "registered_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public String getUsername() { return this.login; }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }


    public RegisteredUser() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
