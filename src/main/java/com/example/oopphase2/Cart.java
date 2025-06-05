package com.example.oopphase2;
import java.util.ArrayList;

import static com.example.oopphase2.Database.products;

public class Cart implements Displayable {

    private ArrayList<Product> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public boolean addToCart(int productId, String quantityStr) {
        int quantity = Integer.parseInt(quantityStr);
        for (Product cartProduct : cartItems) {
            if (cartProduct.getProductId() == productId) {
                for (Product productInDatabase : products) {
                    if (productInDatabase.getProductId() == productId) {
                        int currentQuantityInCart = cartProduct.getQuantity();
                        int newTotalQuantity = currentQuantityInCart + quantity;
                        if (productInDatabase.getStock() >= newTotalQuantity - currentQuantityInCart) {
                            cartProduct.setQuantity(newTotalQuantity);
                            productInDatabase.setStock(productInDatabase.getStock() - quantity);
                            System.out.println("Updated quantity of " + cartProduct.getName() + " to " + newTotalQuantity);
                            return true;
                        } else {
                            System.out.println("Insufficient stock for " + productInDatabase.getName() + ". Available stock: " + productInDatabase.getStock());
                            return false;
                        }
                    }
                }
            }
        }
        for (Product product : products) {
            if (product.getProductId() == productId) {
                if (product.getStock() >= quantity) {
                    Product cartProduct = new Product(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCategory(), product.getImagePath());
                    cartProduct.setQuantity(quantity);
                    cartItems.add(cartProduct);
                    product.setStock(product.getStock() - quantity);
                    System.out.println(quantity + " unit(s) of " + product.getName() + " added to the cart.");
                    return true;
                } else {
                    System.out.println("Insufficient stock for " + product.getName() + ". Available stock: " + product.getStock());
                    return false;
                }
            }
        }
        System.out.println("Product with ID " + productId + " not found.");
        return false;
    }

    public boolean editCartQuantity(int productId, int newQuantity) {
        for (Product cartProduct : cartItems) {
            if (cartProduct.getProductId() == productId) {
                for (Product productInDatabase : products) {
                    if (productInDatabase.getProductId() == productId) {
                        int currentQuantityInCart = cartProduct.getQuantity();
                        int stockChange = newQuantity - currentQuantityInCart;
                        if (productInDatabase.getStock() >= stockChange) {
                            productInDatabase.setStock(productInDatabase.getStock() - stockChange);
                            cartProduct.setQuantity(newQuantity);
                            System.out.println("Quantity of " + cartProduct.getName() + " updated to " + newQuantity);
                            return true;
                        } else {
                            System.out.println("Insufficient stock for " + cartProduct.getName() + ". Available stock: " + productInDatabase.getStock());
                            return false;
                        }
                    }
                }
            }
        }
        System.out.println("Product with ID " + productId + " not found in the cart.");
        return false;
    }



    public boolean removeFromCart(int productId) {
        for (int i = 0; i < cartItems.size(); i++) {
            Product cartProduct = cartItems.get(i);
            if (cartProduct.getProductId() == productId) {
                for (Product product : products) {
                    if (product.getProductId() == productId) {
                        product.setStock(product.getStock() + cartProduct.getQuantity());
                        break;
                    }
                }
                cartItems.remove(i);
                System.out.println(cartProduct.getName() + " removed from the cart.");
                return true;
            }
        }
        System.out.println("Product with ID " + productId + " not found in the cart.");
        return false;
    }
    public void clearCart() {
        for (Product cartProduct : cartItems) {
            for (Product productInDatabase : products) {
                if (productInDatabase.getProductId() == cartProduct.getProductId()) {
                    productInDatabase.setStock(productInDatabase.getStock() + cartProduct.getQuantity());
                    break;
                }
            }
        }
        cartItems.clear();
        System.out.println("Cart has been cleared.");
    }

    public ArrayList<Product> viewCart() {
        return cartItems;
    }

    public double calculateTotal(){
        double total =0;
        for (Product product: cartItems){
            total += product.getPrice()*product.getQuantity();
        }
        return total;
    }
    @Override
    public void display(){
        System.out.println(this.toString());
    }
    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                '}';
    }
}