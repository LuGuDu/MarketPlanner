package com.lugudu.marketplanner.persistence;

import android.content.Context;

import com.lugudu.marketplanner.entity.Product;
import com.lugudu.marketplanner.entity.ShopList;
import com.lugudu.marketplanner.entity.Ticket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Items {

    public static final String DIRECTORY_NAME = "my_app_data";
    public static final String TICKETS_FILE_NAME = "tickets.txt";
    public static final String SHOP_LISTS_FILE_NAME = "shop_lists.txt";
    public static final String PRODUCTS_FILE_NAME = "products.txt";

    private static Vector<Ticket> tickets = new Vector<>();
    private static Vector<ShopList> shopLists = new Vector<>();
    private static Vector<Product> products = new Vector<>();


    public static Vector<Ticket> getTickets() {
        return tickets;
    }

    public static Vector<ShopList> getShopLists() {
        return shopLists;
    }

    public static Vector<Product> getProducts() {
        return products;
    }

    public static void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public static void removeTicket(int index) {
        tickets.removeElementAt(index);
    }

    public static void addShopList(ShopList shopList) {
        shopLists.add(shopList);
    }

    public static void removeShopList(int index) {
        shopLists.removeElementAt(index);
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    public static void removeProduct(int index) {
        products.removeElementAt(index);
    }


    public static void saveTickets(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(TICKETS_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tickets);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTickets(Context context) {
        try {
            File file = new File(context.getFilesDir(), TICKETS_FILE_NAME);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                tickets = (Vector<Ticket>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } else {
                tickets = new Vector<>();
                saveTickets(context);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveShoplists(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(SHOP_LISTS_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(shopLists);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadShopLists(Context context) {
        try {
            File file = new File(context.getFilesDir(), SHOP_LISTS_FILE_NAME);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                shopLists = (Vector<ShopList>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } else {
                shopLists = new Vector<>();
                saveShoplists(context); // Guarda un nuevo archivo si no existe
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void saveProducts(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(PRODUCTS_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(products);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProducts(Context context) {
        try {
            File file = new File(context.getFilesDir(), PRODUCTS_FILE_NAME);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                products = (Vector<Product>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } else {
                products = new Vector<>();
                saveProducts(context); // Guarda un nuevo archivo si no existe
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
