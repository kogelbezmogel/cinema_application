package com.cinema.bodies;

import java.util.List;

/**
 * Represents information about User to enable body deserialization from post request.
 */
public class BasicUserInfo {

    private String login;

    private List<String> roles;

    public BasicUserInfo() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "BasicUserInfo{" +
                "login='" + login + '\'' +
                ", roles=" + roles +
                '}';
    }
}
