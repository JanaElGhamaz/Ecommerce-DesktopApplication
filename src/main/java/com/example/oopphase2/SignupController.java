package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField address;

    @FXML
    private TextField balance;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField dob;

    @FXML
    private Button loginbutton;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button signupButton;

    @FXML
    private Label label;
    @FXML
    private TextField wallet;

    @FXML
    private void handleLogInButtonAction(MouseEvent event) throws IOException {
        EcommerceApplication.changeScene("Login-view.fxml", (Stage) name.getScene().getWindow());
    }

    @FXML
    public void initialize() {
        comboBox.getItems().addAll("FEMALE", "MALE");
    }

    @FXML
    public void handleComboBoxAction() {
        String selectedOption = comboBox.getValue();
        System.out.println("Selected: " + selectedOption);
    }

    public void usersignUp(MouseEvent event) throws IOException {
        signUp();
    }

    public void signUp() throws IOException {
        if (name.getText() == null || name.getText().isEmpty()) {
            label.setText("Username cannot be empty.");
            return;
        }

        if (password.getText() == null || password.getText().isEmpty()) {
            label.setText("Password cannot be empty.");
            return;
        }

        if (dob.getText() == null || dob.getText().isEmpty()) {
            label.setText("Date of birth cannot be empty.");
            return;
        }

        if (address.getText() == null || address.getText().isEmpty()) {
            label.setText("Address cannot be empty.");
            return;
        }

        if (comboBox.getValue() == null || comboBox.getValue().isEmpty()) {
            label.setText("Gender cannot be empty.");
            return;
        }
        if (wallet.getText() == null || wallet.getText().isEmpty()) {
            label.setText("Wallet Amount cannot be empty.");
            return;
        }
        try {
            Double.parseDouble(wallet.getText());
        }catch (NumberFormatException w){label.setText("enter a number for balance");return;}

        if (Customer.signup(name.getText(), password.getText(), dob.getText(), address.getText(), Gender.valueOf(comboBox.getValue()),wallet.getText())) {
            EcommerceApplication.showSuccessMessage("Signed up Successfully");
            EcommerceApplication.changeScene("Login-view.fxml", (Stage) name.getScene().getWindow());
        } else {
            label.setText("Username already signed up, please enter a new username.");
        }
    }



}