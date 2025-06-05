package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class PlaceOrderController {
    @FXML
    private TextField name;
    @FXML
    private CheckBox cardPaymentCheckBox;
    @FXML
    private CheckBox cashPaymentCheckBox;
    @FXML
    private Button confirmPaymentButton;
    @FXML
    private Button returnToCartButton;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField cardExpiryField;
    @FXML
    private TextField cardCvvField;
    @FXML
    private TextField addressField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label totalLabel;

    private Customer currentCustomer;

    public void setCurrentCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: currentCustomer is null in setCurrentCustomer.");
        } else {
            System.out.println("Setting customer: " + customer.getUsername());
        }
        this.currentCustomer = customer;
        initializeCustomerData();
    }

    private void initializeCustomerData() {
        if (currentCustomer == null) {
            System.out.println("Error: currentCustomer is null in PlaceOrderController initialize.");
        } else {
            System.out.println("Logged-in customer: " + currentCustomer.getUsername());
            addressField.setText(currentCustomer.getAddress());
            name.setText(currentCustomer.getUsername());
            System.out.println("Cart items: " + currentCustomer.getCart().viewCart());
        }
        calculateAndDisplayTotal();
    }

    @FXML
    public void initialize() {
        cardNumberField.setVisible(false);
        cardExpiryField.setVisible(false);
        cardCvvField.setVisible(false);
        cardPaymentCheckBox.setOnAction(event -> handleCardPayment());
        cashPaymentCheckBox.setOnAction(event -> handleCashPayment());
        errorLabel.setVisible(false);
    }

    private void handleCardPayment() {
        if (cardPaymentCheckBox.isSelected()) {
            cardNumberField.setVisible(true);
            cardExpiryField.setVisible(true);
            cardCvvField.setVisible(true);
            cashPaymentCheckBox.setSelected(false);
        }
    }

    private void handleCashPayment() {
        if (cashPaymentCheckBox.isSelected()) {
            cardNumberField.setVisible(false);
            cardExpiryField.setVisible(false);
            cardCvvField.setVisible(false);
            cardPaymentCheckBox.setSelected(false);
        }
    }

    private void calculateAndDisplayTotal() {
        if (currentCustomer != null && currentCustomer.getCart() != null) {
            double totalPrice = 0.0;
            for (Product product : currentCustomer.getCart().viewCart()) {
                totalPrice += product.getPrice() * product.getQuantity();
            }
            totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));
        } else {
            totalLabel.setText("Total: $0.00");
        }
    }

    @FXML
    public void confirmPayment() {
        errorLabel.setVisible(false);
        String address = addressField.getText();
        if (address.isEmpty()) {
            errorLabel.setText("Address cannot be empty.");
            errorLabel.setVisible(true);
            return;
        }
        currentCustomer.setAddress(address);

        if (currentCustomer.getCart().viewCart().isEmpty()) {
            errorLabel.setText("Cart is empty. Add items to the cart before placing an order.");
            errorLabel.setVisible(true);
            return;
        }

        Order order = new Order(Database.generateOrderId(), LocalDate.now(), currentCustomer.getCart(), currentCustomer.getAddress());
        double totalPrice = order.calculateTotal();
        totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));
        if (currentCustomer.getBalance() < totalPrice) {
            errorLabel.setText("Insufficient balance. Your balance is less than the total amount.");
            errorLabel.setVisible(true);
            order.paymentStatus = false;
            return;
        }

        if (cardPaymentCheckBox.isSelected()) {
            String cardNumber = cardNumberField.getText();
            String cardExpiry = cardExpiryField.getText();
            String cvv = cardCvvField.getText();

            if (cardNumber.isEmpty() || cardExpiry.isEmpty() || cvv.isEmpty()) {
                errorLabel.setText("Please complete all card details.");
                errorLabel.setVisible(true);
                return;
            }
            if (cardNumber.length() < 16) {
                errorLabel.setText("Card number must be at least 16 digits.");
                errorLabel.setVisible(true);
                return;
            }
            if (!cardNumber.matches("\\d+")) {
                errorLabel.setText("Card number must contain only digits.");
                errorLabel.setVisible(true);
                return;
            }
            order.paymentCredit(cardNumber, cardExpiry, cvv);
        } else if (cashPaymentCheckBox.isSelected()) {
            order.paymentCash();
        } else {
            errorLabel.setText("Please select a payment method.");
            errorLabel.setVisible(true);
            return;
        }

        if (order.getPaymentStatus()) {
            currentCustomer.setBalance(currentCustomer.getBalance() - totalPrice);
            currentCustomer.orders.add(order);
            currentCustomer.getCart().clearCart();
            System.out.println("Order placed successfully!");
            order.print();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Orders.fxml"));
                Parent ordersPage = loader.load();
                OrdersController ordersController = loader.getController();
                ordersController.setCurrentCustomer(currentCustomer);
                Scene ordersScene = new Scene(ordersPage);
                Stage stage = (Stage) confirmPaymentButton.getScene().getWindow();
                stage.setScene(ordersScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void backToCart(MouseEvent event) throws IOException {
        goBackToCart();
    }

    public void goBackToCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
            Parent cartPage = loader.load();
            CartController cartController = loader.getController();
            cartController.setCustomer(currentCustomer);
            Scene cartScene = new Scene(cartPage);
            Stage stage = (Stage) returnToCartButton.getScene().getWindow();
            stage.setScene(cartScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}