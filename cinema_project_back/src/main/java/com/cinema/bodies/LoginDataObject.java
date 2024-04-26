package com.cinema.bodies;


/**
 * Represents login form to enable body deserialization from post request.
 */
public class LoginDataObject {

    private String login;

    private String password;

    public LoginDataObject(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginDataObject() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUserForm{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
