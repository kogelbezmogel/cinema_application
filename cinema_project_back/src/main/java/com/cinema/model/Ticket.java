package com.cinema.model;

import jakarta.persistence.*;

/**
 * Ticket table representation from database
 */
@Entity
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_generator" )
    @SequenceGenerator( name = "tickets_generator", sequenceName = "tickets_seq", allocationSize = 1)
    private Long id;
    @Column(length = 60)
    private String buyer_fname;
    @Column(length = 60)
    private String buyer_lname;
    @Column(length = 60)
    private String buyer_mail;
    @Column
    private Long user_id;
    @ManyToOne
    @JoinColumn( name = "sit_id" )
    private Sit sit;
    @ManyToOne()
    @JoinColumn( name = "show_id" )
    private Show show;


    public Ticket() {
    }

    public Ticket(String buyer_fname, String buyer_lname, String buyer_mail, Long user_id, Sit sit, Show show) {
        this.buyer_fname = buyer_fname;
        this.buyer_lname = buyer_lname;
        this.buyer_mail = buyer_mail;
        this.user_id = user_id;
        this.sit = sit;
        this.show = show;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getBuyer_fname() {
        return buyer_fname;
    }

    public void setBuyer_fname(String buyer_fname) {
        this.buyer_fname = buyer_fname;
    }

    public String getBuyer_lname() {
        return buyer_lname;
    }

    public void setBuyer_lname(String buyer_lname) {
        this.buyer_lname = buyer_lname;
    }

    public String getBuyer_mail() {
        return buyer_mail;
    }

    public void setBuyer_mail(String buyer_mail) {
        this.buyer_mail = buyer_mail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sit getSit() {
        return sit;
    }

    public void setSit(Sit sit) {
        this.sit = sit;
    }

    public Long getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "buyer_fname='" + buyer_fname + '\'' +
                ", buyer_lname='" + buyer_lname + '\'' +
                ", buyer_mail='" + buyer_mail + '\'' +
                ", user_id=" + user_id +
                ", sit=" + sit +
                ", show=" + show +
                '}';
    }
}
