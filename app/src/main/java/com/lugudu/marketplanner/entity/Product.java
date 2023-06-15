package com.lugudu.marketplanner.entity;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {

    private String id; //UUID String
    private String market;
    private double price;
    private String name;
    private String category;

    public Product(String market, double price, String name, String category) {
        this.id = UUID.randomUUID().toString();
        this.market = market;
        this.price = price;
        this.name = name;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", market='" + market + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
