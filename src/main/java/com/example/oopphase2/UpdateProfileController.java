package com.example.oopphase2;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateProfileController {
    @FXML
    private Button backbutton;

    @FXML
    private TextField DOB;

    @FXML
    private TextField address;

    @FXML
    private TextField balance;

    @FXML
    private TextField interests;

    @FXML
    private TextField password;

    @FXML
    private TextField username;
    @FXML
    private ComboBox<String> gender;




    private Customer currentCustomer;
    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }

    @FXML
    public void initialize() {
        gender.getItems().addAll("FEMALE", "MALE");
    }

    @FXML
    public void handleComboBoxAction() {
        String selectedOption = gender.getValue();
        System.out.println("Selected: " + selectedOption);
    }


    @FXML
    public  void Update(MouseEvent event) throws IOException {
        boolean empty=false;

        if (!username.getText().isEmpty()) {
            currentCustomer.setUsername(username.getText());
            empty=true;
        }
        if (!password.getText().isEmpty()) {
            currentCustomer.setPassword(password.getText());
            empty=true;
        }
        if (!DOB.getText().isEmpty()) {
            currentCustomer.setDateOfBirth(DOB.getText());
            empty=true;
        }
        if (!address.getText().isEmpty()) {
            currentCustomer.setAddress(address.getText());
            empty=true;
        }
        if (!balance.getText().isEmpty()) {
            try {
                currentCustomer.setBalance(Double.parseDouble(balance.getText()));
                empty=true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance format! Please enter a numeric value.");
                return;
            }
        }
        if (gender.getValue() != null) {
            currentCustomer.setGender(Gender.valueOf(gender.getValue()));
            empty=true;
        }
        if (!interests.getText().isEmpty()) {
            currentCustomer.setInterests(new ArrayList<>(Arrays.asList(interests.getText().split(","))));
        }

        if (!empty){
            return;
        }

        EcommerceApplication.showSuccessMessage("Customer info updated successfully!");
        changeToProfile("Profile.fxml", currentCustomer);

    }


    @FXML
    public void back(MouseEvent event)throws IOException{
        changeToProfile("Profile.fxml", currentCustomer);
    }

    private void changeToProfile(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        ProfileController profileController = loader.getController();
        profileController.setCurrentCustomer(customer);
        Stage stage = (Stage) gender.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}