package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    @FXML
    private Label welcome;
    @FXML
    private Button Backtologin;

    @FXML
    private Button addProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button showAllProductButton;

    @FXML
    private Button updateProductButton;

    public static Admin currentadmin;

    public static void setCurrentadmin() {
        for (int i = 0; i < Database.admins.size(); i++) {
            if (Database.admins.get(i).getIn() == true) {
                currentadmin = Database.admins.get(i);
            }
        }
    }
    @FXML
    public void initialize() {
        setCurrentadmin();
        if (currentadmin != null) {
            welcome.setText("Welcome, " + currentadmin.getUsername());
        } else {
            welcome.setText("Welcome, Admin");
        }
    }

    @FXML
    void addProduct(MouseEvent event) throws IOException {
        goToAddProduct();
    }

    @FXML
    void deleteProduct(MouseEvent event) throws IOException {
        EcommerceApplication.changeScene("delete.fxml", (Stage) addProductButton.getScene().getWindow());
    }

    @FXML
    void showAllProduct(MouseEvent event)throws IOException {
        EcommerceApplication.changeScene("ShowAll.fxml", (Stage) addProductButton.getScene().getWindow());
    }

    @FXML
    void updateProduct(MouseEvent event) throws IOException {
        EcommerceApplication.changeScene("update.fxml", (Stage) addProductButton.getScene().getWindow());
    }

    @FXML
    void goToLogin(MouseEvent event) throws IOException {
        for (int i = 0; i < Database.admins.size(); i++) {
            if (Database.admins.get(i).getIn()) {
                Database.admins.get(i).setIn(false);
                EcommerceApplication.changeScene("Login-view.fxml", (Stage) addProductButton.getScene().getWindow());
            }
        }
    }

    public void goToAddProduct() throws IOException {
        EcommerceApplication.changeScene("Create.fxml", (Stage) addProductButton.getScene().getWindow());
    }
}