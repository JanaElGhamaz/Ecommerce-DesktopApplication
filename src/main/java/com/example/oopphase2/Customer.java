package com.example.oopphase2;
import java.lang.reflect.Array;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Random;


enum Gender {MALE, FEMALE};
public class Customer extends Person implements Displayable {
    private double balance;
    private String address;
    private Gender gender;
    private ArrayList<String> interests;
    private Cart cart;
    public ArrayList<Order> orders;
    private static int idCounter = 1;


    Customer(){
        cart = new Cart();
        interests = new ArrayList<String>();
        orders = new ArrayList<Order>();
        Database.customers.add(this);
    }
    Customer(String username,String password){
        super(username, password);
        cart = new Cart();
        orders = new ArrayList<>();
        for (Customer customer : Database.customers) {
            if (customer.getUsername().equals(username)) {
                this.dateOfBirth= customer.dateOfBirth;
                this.balance = customer.balance;
                this.address = customer.address;
                this.gender = customer.gender;
                this.interests = new ArrayList<>(customer.interests);
                this.orders = new ArrayList<>(customer.orders);
                break;
            }
        }
    }
    Customer(String username,String password,String dateOfBirth,double balance,String address,Gender gender){
        super(username, password, dateOfBirth);
        cart = new Cart();
        interests = new ArrayList<String>();
        orders = new ArrayList<Order>();
        this.balance=balance;
        this.gender=gender;
        this.address=address;
        Database.customers.add(this);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    public Cart getCart(){
        return cart;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Gender getGender() {
        return gender;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public static boolean signup(String username1, String password1, String date, String address, Gender gender, String wallet) {
        Customer a = new Customer();
        double balance = Double.parseDouble(wallet);
        //Scanner input = new Scanner(System.in);
        //System.out.println("Enter username \n");
        //String username1 = input.nextLine();
        //boolean x = true;
        for (int i = 0; i < Database.customers.size(); i++) {
            if (username1.equals(Database.customers.get(i).getUsername())) {
                System.out.println("username already signed in \n Please enter new username ");
                //username1 = input.nextLine();
                return false;
            }
        }
        a.setUsername(username1);
        a.setPassword(password1);
        a.gender = gender;
        a.setAddress(address);
        a.setDateOfBirth(date);
        a.setBalance(balance);
        return true;
        //System.out.println("Enter Password \n");
        //String password1 = input.nextLine();
        //System.out.println("Enter gender (MALE, FEMALE):");
        //String s = input.nextLine().trim().toUpperCase();
        //System.out.println("Enter Address \n");
        //System.out.println("Enter BirthDate \n");

    }

    public void placeOrder(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(100) + 1;
        Order order = new Order(randomNumber, LocalDate.now(), getCart(), this.address);
        this.orders.add(order);
        Database.orders.add(order);
    }


    @Override
    public void display(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return super.toString() + " " +
                "Customer{" +
                "balance=" + balance +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", interests=" + interests +
                ", cart=" + cart +
                ", orders=" + orders.size()+
                '}';
    }

    public String Print(){
        return super.Print() +  "\n" + "\n" +
                "Balance:       " + balance + "\n" + "\n" +
                "Address:    " + address + "\n" + "\n" +
                "Gender:      " + gender + "\n" + "\n" +
                "Interests:   " + interests + "\n" + "\n" +
                "Orders:      " + orders.size() + " order(s)";
    }
}













































































