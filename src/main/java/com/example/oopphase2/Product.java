package com.example.oopphase2;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Scanner;

public class Product implements Displayable{
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private String imagePath;
    private int quantity;

    public Product(int x){

    }
    public Product(int productId, String name, String description, double price, int stock, String category,String imagePath) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imagePath=imagePath;
        this.quantity= 1;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public boolean setCategory(String category) {
        if (category == null) {
            this.category = null;
            return true;
        }

        for (int i = 0; i < Database.categories.size(); i++) {
            if (category.equals(Database.categories.get(i).getName()) ){
                this.category = category;
                return true;
            }
        }
        return false;
    }

    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public String getImagePath() {return imagePath;}

    public int getQuantity() {
        return quantity;
    }

    @Override
    public void display(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Product{" +
                "stock=" + stock +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}