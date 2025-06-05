package com.example.oopphase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class CartController {
    @FXML
    private Label error;
    @FXML
    private Button Homebutton;
    @FXML
    private Button Placeorder;
    @FXML
    private TableView<Product> cartTable;
    @FXML
    private TableColumn<Product, String> imageColumn;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Void> updateQuantityColumn;
    @FXML
    private TableColumn<Product, Void> removeColumn;

    @FXML
    private Label totalPriceLabel;

    private ObservableList<Product> cartItems = FXCollections.observableArrayList();
    private Customer currentCustomer;

    public void setCustomer(Customer customer) {
        if (customer == null) {
        } else {
            System.out.println("Setting customer: " + customer.getUsername());
        }
        this.currentCustomer = customer;
        loadCartItems();
    }

    @FXML
    public void initialize() {
        if (cartItems == null) {
            cartItems = FXCollections.observableArrayList();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + imagePath, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        updateQuantityColumn.setCellFactory(createUpdateQuantityButton());
        removeColumn.setCellFactory(createRemoveButton());
        loadCartItems();
    }

    private void loadCartItems() {
        if (currentCustomer != null) {
            cartItems = FXCollections.observableArrayList(currentCustomer.getCart().viewCart());
            cartTable.setItems(cartItems);
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        if (currentCustomer != null && currentCustomer.getCart() != null) {
            double totalPrice = currentCustomer.getCart().calculateTotal();
            totalPriceLabel.setText(String.format("Total Price: %.2f", totalPrice));
        }
    }

    private Callback<TableColumn<Product, Void>, TableCell<Product, Void>> createUpdateQuantityButton() {
        return column -> new TableCell<>() {
            private final TextField quantityField = new TextField();{
                quantityField.setPromptText("Qty");
                quantityField.setPrefWidth(50);
                quantityField.setAlignment(Pos.CENTER);
            }
            private final Button updateButton = new Button("✔");{
                updateButton.setOnAction(event -> {
                    Product product = getTableRow().getItem();
                    try {
                        int newQuantity = Integer.parseInt(quantityField.getText());

                        if (newQuantity < 0) {
                            System.out.println("Invalid quantity. Quantity cannot be negative.");
                            return;
                        }
                        boolean success = currentCustomer.getCart().editCartQuantity(product.getProductId(), newQuantity);

                        if (success) {
                            cartTable.refresh();
                            updateTotalPrice();
                        } else {
                            System.out.println("Failed to update quantity. Stock may be insufficient.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity. Please enter a numeric value.");
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, quantityField, updateButton);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(5);
                    setGraphic(hbox);
                }
            }
        };
    }

    private Callback<TableColumn<Product, Void>, TableCell<Product, Void>> createRemoveButton() {
        return column -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");{
                removeButton.setOnAction(event -> {
                    Product product = getTableRow().getItem();
                    boolean success = currentCustomer.getCart().removeFromCart(product.getProductId());

                    if (success) {
                        loadCartItems();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        };
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
        Stage stage = (Stage) cartTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void placeOrder(MouseEvent event) throws IOException {
        if (currentCustomer != null) {
            if (currentCustomer.getCart().viewCart().isEmpty()) {
                error.setText("Can't place order, Add items to cart first");
                error.setVisible(true);
            } else {
                System.out.println("Passing customer: " + currentCustomer.getUsername());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaceOrder.fxml"));
                Parent root = loader.load();
                PlaceOrderController placeOrderController = loader.getController();
                placeOrderController.setCurrentCustomer(currentCustomer);
                System.out.println("Customer set in PlaceOrderController.");
                Stage stage = (Stage) cartTable.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }

    @FXML
    public void Orders(MouseEvent event) throws IOException {
        changeToOrders("Orders.fxml", currentCustomer);
    }

    private void changeToOrders(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        OrdersController ordersController = loader.getController();
        ordersController.setCurrentCustomer(customer);
        Stage stage = (Stage) cartTable.getScene().getWindow();
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
        Stage stage = (Stage) cartTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}