package com.lugudu.marketplanner.persistence;

import com.lugudu.marketplanner.entity.ShopList;
import com.lugudu.marketplanner.entity.Ticket;

import java.util.Vector;

public class Items {

    private static Vector<Ticket> tickets = new Vector<>();
    private static Vector<ShopList> shopLists = new Vector<>();


    public static Vector<Ticket> getTickets() {
        return tickets;
    }

    public static Vector<ShopList> getShopLists() { return shopLists; }

    public static void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public static void removeTicket(int index){
        tickets.removeElementAt(index);
    }

    public static void addShopList(ShopList shopList) {
        shopLists.add(shopList);
    }

    public static void removeShopList(int index){
        shopLists.removeElementAt(index);
    }
}
