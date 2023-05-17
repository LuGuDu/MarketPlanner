package com.lugudu.marketplanner.entity;

import android.os.Build;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ShopList {

    private String id;
    private String name;
    private LocalDate creation_date;
    private LocalDate  modify_date;
    private List<String> items;

    public ShopList (String name, LocalDate  modify_date, List<String> items){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.creation_date = LocalDate.now();
        }
        this.modify_date = modify_date;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDate getModify_date() {
        return modify_date;
    }

    public void setModify_date(LocalDate modify_date) {
        this.modify_date = modify_date;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShopList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", creation_date=" + creation_date +
                ", modify_date=" + modify_date +
                ", items=" + items +
                '}';
    }
}
