package com.cinema.bodies;


public class UpdateUserDataObject {

    String login;

    RegistrationDataObject dataToUpdate;

    public UpdateUserDataObject() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RegistrationDataObject getDataToUpdate() {
        return dataToUpdate;
    }

    public void setDataToUpdate(RegistrationDataObject dataToUpdate) {
        this.dataToUpdate = dataToUpdate;
    }
}
