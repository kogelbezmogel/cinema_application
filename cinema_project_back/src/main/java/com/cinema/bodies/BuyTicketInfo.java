package com.cinema.bodies;

import java.util.List;

/**
 * Represents information about buying ticket transaction to enable body deserialization from post request.
 */
public class BuyTicketInfo {
    private List<Integer> sits_nums;
    private BuyerInfo buyerInfo;
    private String userLogin;
    private Long room_id;
    private Long show_id;

    public BuyTicketInfo() {
    }

    public List<Integer> getSits_nums() {
        return sits_nums;
    }

    public void setSits_nums(List<Integer> sits_nums) {
        this.sits_nums = sits_nums;
    }

    public BuyerInfo getBuyerInfo() {
        return buyerInfo;
    }

    public void setBuyerInfo(BuyerInfo buyerInfo) {
        this.buyerInfo = buyerInfo;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getShow_id() {
        return show_id;
    }

    public void setShow_id(Long show_id) {
        this.show_id = show_id;
    }

    @Override
    public String toString() {
        return "BuyTicketInfo{" +
                "sits_nums=" + sits_nums +
                ", buyerInfo=" + buyerInfo +
                ", userLogin='" + userLogin + '\'' +
                ", room_id=" + room_id +
                ", show_id=" + show_id +
                '}';
    }
}
