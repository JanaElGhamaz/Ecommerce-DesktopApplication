package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button exitbutton;
    @FXML
    private Button loginbutton;
    @FXML
    private Label newuser;
    @FXML
    private Pane pane;
    @FXML
    private PasswordField password;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button signupbutton;
    @FXML
    private StackPane stack;
    @FXML
    private TextField name;
    @FXML
    private Label label;

    @FXML
    private void handleExitButtonAction(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void userLogIn(MouseEvent event) throws IOException {
        checkLogin();
    }

    public void checkLogin() throws IOException {
        String username = name.getText();
        String userPassword = password.getText();

        if (username == null || username.isEmpty()) {
            label.setText("Username cannot be empty.");
            return;
        }
        if (userPassword == null || userPassword.isEmpty()) {
            label.setText("Password cannot be empty.");
            return;
        }

        int loginResult = Person.login(username, userPassword);

        if (loginResult == 2) {
            Customer loggedInCustomer = (Customer) Person.getLoggedInUser();
            changeSceneWithCustomer("Customer.fxml", loggedInCustomer);
        } else if (loginResult == 1) {
            AdminController.setCurrentadmin();
            EcommerceApplication.changeScene("Admin.fxml", (Stage) name.getScene().getWindow());
        } else {
            label.setText("Incorrect username or password");
        }
    }

    private void changeSceneWithCustomer(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        CustomerController customerController = loader.getController();
        System.out.println("Passing customer: " + customer.getUsername());
        customerController.setCurrentCustomer(customer);
        Stage stage = (Stage) name.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void signup(MouseEvent event) throws IOException {
        EcommerceApplication.changeScene("Signup-view.fxml", (Stage) name.getScene().getWindow());
    }
}

