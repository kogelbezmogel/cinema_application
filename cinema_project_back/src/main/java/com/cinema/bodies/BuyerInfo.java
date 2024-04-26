package com.cinema.bodies;

/**
 * Represents information about buyer in BuyTicketInfo class to enable body deserialization from post request.
 */
public class BuyerInfo {

    String fname;
    String lname;
    String mail;

    public BuyerInfo() {
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

    @Override
    public String toString() {
        return "BuyerInfo{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
