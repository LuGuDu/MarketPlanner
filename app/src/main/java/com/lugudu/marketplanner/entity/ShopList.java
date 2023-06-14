package com.lugudu.marketplanner.entity;

import java.util.List;
import java.util.UUID;

public class ShopList {

    private String id;
    private String name;
    private List<ListItem> items;

    public ShopList (String name, List<ListItem> items){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShopList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items.size() +
                '}';
    }
}
