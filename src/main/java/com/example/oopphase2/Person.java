package com.example.oopphase2;

import java.util.Scanner;

public abstract class Person {
    protected String username;
    protected String password;
    protected String dateOfBirth;
    private static Person loggedInUser;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    Person() {
    }

    Person(String username, String password) {
        this.password = password;
        this.username = username;
    }

    Person(String username, String password, String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.username = username;
    }

    public static int login(String username1, String password1) {
        boolean admin, x = true;


        for (int i = 0; i < Database.admins.size(); i++) {
            if (username1.equals(Database.admins.get(i).getUsername()) && password1.equals(Database.admins.get(i).getPassword())) {
                x = false;
                Database.admins.get(i).setIn(true);
                return 1;
            }

        }
        for (int i = 0; i < Database.customers.size(); i++) {
            if (username1.equals(Database.customers.get(i).getUsername()) && password1.equals(Database.customers.get(i).getPassword())) {
                loggedInUser = Database.customers.get(i);
                x = false;
                return 2;
            }
        }
        return 0;
    }

    @Override
    public String toString(){
        return "Your Username is : " + username + " \nYour Password is : " + password + "\nYour date of birth is : " + dateOfBirth;
    }

    public static Person getLoggedInUser() {
        return loggedInUser;
    }

    public String Print() {
        return "Username:    " + username + "\n" + "\n" +
                "Password:   " + password + "\n" + "\n" +
                "Date of Birth:    " + dateOfBirth;
    }
}

