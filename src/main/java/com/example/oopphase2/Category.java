package com.example.oopphase2;

import java.util.ArrayList;

public class Category implements Displayable{
    private static int idCounter = 1;
    private int id;
    private String name;
    private String description;
    private ArrayList<Product> products;

    public Category(){}

    public Category(String name, String description) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
            product.setCategory(this.name);
        }
    }

    @Override
    public void display(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }

}