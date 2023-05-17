package com.lugudu.marketplanner.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

    private String id; //UUID String
    private String location;
    private String market;
    private double totalPrice;
    private String name;
    private LocalDate date;

    public Ticket (String location, String market, double totalPrice, String name, LocalDate date){
        this.id = UUID.randomUUID().toString();
        this.location = location;
        this.market = market;
        this.totalPrice = totalPrice;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", market='" + market + '\'' +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
