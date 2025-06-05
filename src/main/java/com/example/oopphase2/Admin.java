package com.example.oopphase2;

import java.util.ArrayList;

public class Admin<T> extends Person implements CRUD<T> , Displayable{
    private String role;
    private double workingHours;
    private boolean in = false;
    private Class<T> type;

    public Admin(Class<T> type,String username,String password,String dateOfBirth) {
        super(username, password, dateOfBirth);
        this.type = type;
        if(type == Product.class) {
            role = "Supplier";
        }
        else{
            role = "Manager";
        }
        Database.admins.add(this);
    }

    public void setIn(boolean in) {
        this.in = in;
    }
    public boolean getIn(){
        return in;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    @Override
    public void display(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return super.toString() + " " +
                "Admin{" +
                "role='" + role + '\'' +
                ", workingHours=" + workingHours +
                '}';
    }

    @Override
    public boolean create(T item) {
        if (type == Product.class) {
            Product product = (Product) item;
            for(Product p : Database.products) {
                if (p.getProductId() == product.getProductId()) {
                    System.out.println("Product with ID " + product.getProductId() + " already exists.");
                    return false;
                }
            }
            Database.products.add(product);
            System.out.println("Product added: " + item);
            return true;
        } else if (type == Category.class) {
            Category categoryy=(Category) item;

            for (Category category : Database.categories) {
                if (category.getName().equals(categoryy.getName())) {
                    System.out.println("The category '" + categoryy.getName() + "' already exists.");
                    return false;
                }
            }
            Database.categories.add(categoryy);
            System.out.println("Category created: " + item);
            return true;
        }
        else {
            System.out.println("U can only  create a product or category");
            return false;
        }
    }

    @Override
    public ArrayList<T> read() {
        if (type == Product.class) {
            return (ArrayList<T>) Database.products;
        } else {
            return (ArrayList<T>) Database.categories;
        }
    }

    @Override
    public boolean update(int id, T updatedItem) {
        if (type == Product.class) {
            Product updatedProduct = (Product) updatedItem;
            for (Product p : Database.products) {
                if (p.getProductId() == id) {
                    if (updatedProduct.getName() != null && !updatedProduct.getName().isEmpty()) {
                        p.setName(updatedProduct.getName());
                    }
                    if (updatedProduct.getDescription() != null && !updatedProduct.getDescription().isEmpty()) {
                        p.setDescription(updatedProduct.getDescription());
                    }
                    if (updatedProduct.getPrice() >= 0) {
                        p.setPrice(updatedProduct.getPrice());
                    }
                    if (updatedProduct.getStock() >= 0) {
                        p.setStock(updatedProduct.getStock());
                    }
                    if (updatedProduct.getCategory() != null && !updatedProduct.getCategory().isEmpty()) {
                        p.setCategory(updatedProduct.getCategory());
                    }
                    System.out.println("Product updated successfully!");
                    return true;
                }
            }
            System.out.println("Product not found with the given ID.");
            return false;
        } else if (type == Category.class) {
            Category updatedCategory = (Category) updatedItem;
            for (Category category : Database.categories) {
                if (category.getId() == id) {
                    if (updatedCategory.getName() != null && !updatedCategory.getName().isEmpty()) {
                        category.setName(updatedCategory.getName());
                    }
                    if (updatedCategory.getDescription() != null && !updatedCategory.getDescription().isEmpty()) {
                        category.setDescription(updatedCategory.getDescription());
                    }
                    System.out.println("Category updated: " + category);
                    return true;
                }
            }
            System.out.println("Category not found with the given ID.");
            return false;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (type == Product.class) {
            for (int i = 0; i < Database.products.size(); i++) {
                if (Database.products.get(i).getProductId() == id) {
                    Database.products.remove(i);
                    return true;
                }
            }
            return false;
        } else if (type == Category.class) {
            for (int i = 0; i < Database.categories.size(); i++) {
                Category category = Database.categories.get(i);
                if (category.getId() == id) {
                    for (Product product : category.getProducts()) {
                        product.setCategory(null);
                    }
                    Database.categories.remove(i);
                    System.out.println("Category deleted: " + category);
                    return true;
                }
            }
            System.out.println("Category not found with the given ID.");
            return false;
        }
        return false;
    }


    public void showAll(){
        System.out.println(Database.admins);
        System.out.println(Database.customers);
        System.out.println(Database.products);
        System.out.println(Database.categories);
        System.out.println(Database.orders);
    }
}

