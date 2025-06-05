package com.example.oopphase2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class OrdersController {

    private Customer currentCustomer;

    @FXML
    private Label totalAmountLabel;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> orderDateColumn;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private Button profile;

    @FXML
    private Button Homebutton;


    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
        loadOrders();
    }

    private void loadOrders() {
        if (currentCustomer != null && !currentCustomer.getOrders().isEmpty()) {
            ObservableList<Order> orders = FXCollections.observableArrayList(currentCustomer.getOrders());
            ordersTable.setItems(orders);

            orderIdColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(cellData.getValue().getOrderId())));
            orderDateColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getOrderDate().toString()));
        }
    }

    @FXML
    private void handleOrderSelection(MouseEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            ObservableList<Product> products = FXCollections.observableArrayList(selectedOrder.getProducts());
            productsTable.setItems(products);

            double totalAmount = 0.0;
            for (Product product : selectedOrder.getProducts()) {
                totalAmount += product.getPrice() * product.getQuantity();
            }
            totalAmountLabel.setText("Total Amount: $" + totalAmount);
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
        Stage stage = (Stage) ordersTable.getScene().getWindow();
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
        Stage stage = (Stage) ordersTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void Profile(MouseEvent event) throws IOException {
        changeToProfile("Profile.fxml", currentCustomer);
    }
    private void changeToProfile(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        ProfileController profileController = loader.getController();
        profileController.setCurrentCustomer(customer);
        Stage stage = (Stage) ordersTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
    }
}
