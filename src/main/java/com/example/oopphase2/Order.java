package com.example.oopphase2;

import java.time.LocalDate;
import java.util.ArrayList;
public class Order implements Displayable{
    private String address;
    private int orderId;
    private LocalDate orderDate;
    private double totalAmount;
    private ArrayList<Product> products;
    public boolean paymentStatus;


    public Order(int orderId, LocalDate orderDate, Cart cart , String address){
        this.orderId= orderId;
        this.orderDate= orderDate;
        this.products= new ArrayList<>(cart.viewCart());
        this.totalAmount= calculateTotal();
        this.paymentStatus= false;
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }



    public ArrayList<Product> getProducts() {
        return products;
    }
    public boolean getPaymentStatus(){
        return paymentStatus;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Product product : this.products) {
            total += product.getPrice() * product.getQuantity();
            System.out.println("Product: " + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
        System.out.println("Total: " + total);
        return total;
    }


    public void paymentCash(){
        System.out.println("Processing payment of $" + totalAmount +" by Cash.");
        paymentStatus = true;
        System.out.println("Cash Payment successful!");
    }

    public void paymentCredit(String cardNumber,String cardExp, String cvv) {
        System.out.println("Processing payment of $" + totalAmount +" by Credit Card.");

        if (cardNumber == null || cardExp == null || cvv == null) {
            System.out.println("Payment failed, card details cannot be empty");
            paymentStatus = false;
            return;
        }
        if (cardNumber.length() != 16) {
            System.out.println("Card Number must be 16 digits long.");
            paymentStatus = false;
            return;
        }
        for (char c : cardNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.out.println("Card Number must contain only digits.");
                paymentStatus = false;
                return;
            }
        }

        if (cvv.length() != 3) {
            System.out.println("Card cvv must be 3 digits long.");
            paymentStatus = false;
            return;
        }

        for (char c : cvv.toCharArray()){
            if (!Character.isDigit(c)) {
                System.out.println("Card cvv must contain only digits.");
                paymentStatus = false;
                return;
            }
        }
        paymentStatus = true;
        System.out.println("Credit payment was successful!");

    }
    public void print() {
        System.out.println("Order Address: " + address);
        System.out.println("Order Id: " + orderId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Amount: $" + calculateTotal());
        System.out.println("Products in this order: ");
        for (Product product : products) {
            System.out.println("- "+ product.getName() + " : $" + product.getPrice() + " (Quantity: "+ product.getStock() +")");
        }
    }

    @Override
    public void display(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Order{" +
                "address=" + address +
                ", orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", products=" + products +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}