package com.cinema.bodies;


/**
 * Represents information about Sit to enable body deserialization from post request.
 */
public class BasicSitsInfo {

    private Integer order_num;
    private String type;


    public BasicSitsInfo(Integer order_num, String type) {
        this.order_num = order_num;
        this.type = type;
    }

    public Integer getOrder_num() {
        return order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BasicSitsInfo{" +
                "order_num=" + order_num +
                ", type='" + type + '\'' +
                '}';
    }
}
