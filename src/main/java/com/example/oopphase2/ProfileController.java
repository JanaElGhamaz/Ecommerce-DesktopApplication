package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private VBox profileVBox;

    @FXML
    private Button logoutButton;
    @FXML
    private Button Update;

    private Customer currentCustomer;

    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
        displayProfile();
    }

    private void displayProfile() {
        profileVBox.getChildren().clear();

        if (currentCustomer != null) {
            String[] customerData = currentCustomer.Print().split(", ");
            for (String data : customerData) {
                Label label = new Label(data);
                label.getStyleClass().add("profile-label");
                profileVBox.getChildren().add(label);
            }
        }
    }



    @FXML
    public void home(MouseEvent event) throws IOException {
        changeToHome("Customer.fxml", currentCustomer);
    }

    private void changeToHome(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        CustomerController customerController = loader.getController();
        customerController.setCurrentCustomer(customer);
        Stage stage = (Stage) profileVBox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void cart(MouseEvent event) throws IOException {
        changeToCart("Cart.fxml", currentCustomer);
    }

    private void changeToCart(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        CartController cartController = loader.getController();
        cartController.setCustomer(customer);
        Stage stage = (Stage) profileVBox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void handleLogoutButton(MouseEvent event) throws IOException {
        EcommerceApplication.changeScene("login-view.fxml", (Stage) logoutButton.getScene().getWindow());
    }
    @FXML
    public void updateinfo(MouseEvent event) throws IOException {
        changeToUpdateInfo("UpdateProfile.fxml", currentCustomer);
    }

    private void changeToUpdateInfo(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        UpdateProfileController updateProfileController = loader.getController();
        updateProfileController.setCurrentCustomer(customer);
        Stage stage = (Stage) profileVBox.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


}
